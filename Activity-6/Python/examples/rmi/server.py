import Pyro4
from Contacts import Contacts


daemon = Pyro4.Daemon()
uri = daemon.register(Contacts)
ns = Pyro4.locateNS()
ns.register("ContactsService", uri)

daemon.requestLoop()

