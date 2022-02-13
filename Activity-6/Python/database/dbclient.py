import Pyro4

Pyro4.locateNS("127.0.0.1", 9090)
db = Pyro4.Proxy("PYRONAME:DatabaseService")


print(db.execute("SELECT * FROM curso"))
print(db.fetch())