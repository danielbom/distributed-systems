import Pyro4
from Database import Database

daemon = Pyro4.Daemon()
uri = daemon.register(Database)
ns = Pyro4.locateNS()
ns.register("DatabaseService", uri)

daemon.requestLoop()

