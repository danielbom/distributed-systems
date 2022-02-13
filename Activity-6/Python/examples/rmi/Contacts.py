import Pyro4

@Pyro4.expose
class Contacts(object):

    def __init__(self):
        self.contacts = []

    def add(self, name, number):
        self.contacts.append((name, number))
    
    def rm(self, key):
        self.contacts = [c for c in self.contacts if key not in c]
    
    def get(self, key):
        return [c for c in self.contacts if key in c]

    def getAll(self):
        return self.contacts