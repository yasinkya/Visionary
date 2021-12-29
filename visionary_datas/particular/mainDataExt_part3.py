import json

import requests

from bs4 import BeautifulSoup

allTheaters = {}
allMovies = {}


def get_movieDetails(mov_url):
    movie_attributes = {}
    req = requests.get(mov_url)

    if req.status_code == 200:
        pcont = BeautifulSoup(req.content, "lxml")

        mov_name = pcont.find("h1", attrs={"itemprop": "name"}).text

        if mov_name not in allMovies.keys():
            description = pcont.find("p", attrs={"itemprop": "description"}).text.strip()
            mov_img = pcont.find("img", attrs={"class": "poster"}).get("src")

            movie_attributes["Description"] = description.replace("\n", "").replace('\"', "")
            movie_attributes["MovieName"] = mov_name
            movie_attributes["Banner"] = mov_img
            movie_attributes["VideoId"] = ""

            for prop in pcont.findAll("div", attrs={"class": "info-group"}):
                try:
                    key = prop.findAll("span")[0].text
                    val = ", ".join(prop.findAll("span")[1].text.replace("  ", "").replace("\n", "").split(","))

                    movie_attributes[key] = val

                except Exception as e:
                    print(e)

            actors = {}
            for actor in pcont.findAll("div", attrs={"itemprop": "actors"}):
                act_name = actor.find("img").get("alt")
                act_image = actor.find("img").get("data-src")

                actors[act_name] = {"Name": act_name, "Image": act_image}

            print(f"Fetching {mov_name}...")

            movie_attributes["Actors"] = actors

            allMovies[mov_name] = movie_attributes

        else:
            print(f"{mov_name} fetching skipped.")


def get_inTheaters(url_of):
    intheaters = []
    req = requests.get(url_of)

    if req.status_code == 200:
        pcont = BeautifulSoup(req.content, "lxml")
        moviecards = pcont.findAll("div", attrs={"class": "card movie-single is-flex"})
        for movie in moviecards:

            name = movie.find("a").text
            intheaters.append(name)

            print(f"filling to in theaters for current movth - {name}...")

            mov_url = movie.find("a").get("href")
            get_movieDetails(mov_url)

    return intheaters


def get_thaters_ofcities(cities):

    for city, url_of_city in cities.items():
        req = requests.get(url_of_city)

        if req.status_code == 200:
            print("\n", city, url_of_city)
            pcontent = BeautifulSoup(req.content, "lxml")

            theaters_cont = [s for s in pcontent.findAll("div", attrs={"class": "card theatre-single"})]
            for cont in theaters_cont:

                theater_attr = {}

                name = cont.find("a", attrs={"class": "title"}).text.strip()
                url_of_theater = cont.find("a", attrs={"class": "title"}).get("href")
                theater_attr["Name"] = name
                theater_attr["Url"] = url_of_theater

                info = cont.findNext("div", attrs={"class": "address left"}).text.replace("\t", "").strip().split("\n")

                theater_attr["Address"] = info[0].replace("Adres:", "")
                theater_attr["Number"] = info[-1].replace("Tel:", "")

                for prp in cont.findAll("li"):
                    key = prp.findAll()[0].get("class")[-1].replace("icon-", "")
                    val = True if "check" in prp.findAll()[1].get("class")[-1].replace("icon-", "") else False
                    theater_attr[key] = val

                print(f"Current mov theater__{name=}__{url_of_theater=}")

                theater_attr["InTheaters"] = get_inTheaters(url_of_theater)
                allTheaters[name] = theater_attr


if __name__ == '__main__':
    url = "https://www.sinemalar.com/sinemasalonlari"
    req = requests.get(url)

    if req.status_code == 200:
        pcontent = BeautifulSoup(req.content, "lxml")
        cities = {i.text: i.get("href") for i in pcontent.findAll("a", attrs={"class": "label city"})}

        with open("part3.json", "r") as part:
            cty = json.loads(part.read())

        get_thaters_ofcities(cty)

        obj_allth = json.dumps(allTheaters, indent=4, ensure_ascii=False)
        obj_allmv = json.dumps(allMovies, indent=4, ensure_ascii=False)

        with open("movTheaters3.json", "w", encoding='utf-8') as file:
            file.write(obj_allth)
            # json.dump(allTheaters, file, ensure_ascii=False)

        with open("allMovies.json", "w", encoding='utf-8') as file:
            file.write(obj_allmv)
            # json.dump(allMovies, file, ensure_ascii=False)
