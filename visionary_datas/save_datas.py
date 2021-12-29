import json
import webbrowser

import firebase_admin
from firebase_admin import db
from firebase_admin import credentials


def save_movies():
    with open("jsons/allMovies.json", "r") as file:
        allmovs = json.loads(file.read())

    ref = db.reference("/Movies/")

    for name, attr in allmovs.items():
        print(name, attr)
        try:
            for key, val in attr.items():
                if key == "":
                    pass
                else:
                    ref.child(name).child(key).set(val)
            # ref.child(name).set(attr)
        except Exception as e:
            print(e)


def save_theaters():
    ref = db.reference("/Movie Theaters/")

    with open("jsons/movTheaters.json", "r") as file:
        alltheaters = json.loads(file.read())

    for city, theaters in alltheaters.items():
        try:
            print("\n", city)
            for theater, attrs in theaters.items():
                print("\t|->", theater)
                for attr, val in attrs.items():
                    ref.child(city).child(theater).child(attr).set(val)
        except Exception as e:
            print(e)


if __name__ == '__main__':
    cred_obj = credentials.Certificate("visionary_certificate.json")
    def_app = firebase_admin.initialize_app(cred_obj,
                                             {"databaseURL": "https://visionary-4e870-default-rtdb.firebaseio.com/"})

    # save_movies()
    save_theaters()


