import Pyro4

class Contact(object):
    def __init__(self, name = "", address = ""):
        self.name = name
        self.address = address


Pyro4.locateNS("127.0.0.1", 9090)
contacts = Pyro4.Proxy("PYRONAME:ContactsService")

print(contacts.add("Daniel", "991413531"))
print(contacts.add("Mara", "988359543"))
print(contacts.getAll())
print(contacts.rm("Daniel"))
print(contacts.get("Daniel"))
print(contacts.getAll())
