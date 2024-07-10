using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;
using persistence;
using services;

namespace Server
{
    public class ProjectServicesImpl:IProjectServices
    {
        public IUserRepository userRepo {  get; set; }
        public IExcursieRepository excursieRepo { get; set; }
        public IBiletRepository biletRepo { get; set; }

        //private readonly IDictionary<int, IProjectObserver> loggedClients;
        private readonly IDictionary<String, IProjectObserver> loggedClients;

        public ProjectServicesImpl(IUserRepository userRep,IExcursieRepository excursieRep,IBiletRepository biletRep)
        {
            /*
            userRepo = userRep;
            excursieRepo = excursieRep;
            biletRepo = biletRep;
            loggedClients = new Dictionary<int, IProjectObserver>();
        */
            userRepo = userRep;
            excursieRepo = excursieRep;
            biletRepo = biletRep;
            loggedClients = new Dictionary<String, IProjectObserver>();
        }

        public void login(User user, IProjectObserver client)
        {
            //            User usr = userRepo.findUser(user.username,user.password);
            //            Console.WriteLine(usr.username);
            //            Console.WriteLine(usr.id);
            //
            //            if (usr.id != 0)
            //            {
            //                if (loggedClients.ContainsKey(usr.id))
            //                {
            //                    throw new ProjectException("User already logged in");
            //                }
            //                loggedClients[user.id] = client;
            //            }
            //            else
            //            {
            //                throw new ProjectException("Auth failed");
            //            }
            Console.WriteLine(user.username);
            User usr = userRepo.findUser(user.username, user.password);

            Console.WriteLine(usr.username);
            if (usr.id != 0)
            {
                if (loggedClients.ContainsKey(usr.username.ToString()))
                {
                    throw new ProjectException("User already logged in");
                }
                loggedClients[user.username.ToString()] = client;
            }
            else
            {
                throw new ProjectException("Auth failed");
            }
        }

        public void logout(User user, IProjectObserver client)
        {
            //IProjectObserver localClient = loggedClients[user.id];
            //if (localClient == null)
            //    throw new ProjectException("User " + user.id + "is not logged in");
            //loggedClients.Remove(user.id);
            IProjectObserver localClient = loggedClients[user.username.ToString()];
            if (localClient == null)
                throw new ProjectException("User " + user.id + "is not logged in");
            loggedClients.Remove(user.username.ToString());
        }

        public Excursie[] getExcursii(IProjectObserver client)
        {
            try
            {
                List<Excursie> excursii;
                excursii = (List<Excursie>)excursieRepo.findAll();

                return excursii.ToArray();
            }
            catch (Exception e)
            {
                throw new ProjectException("Error " + e);
            }
        }

        public Excursie[] getExcursiiLaLocSiOra(Excursie excursie, int oraMin, int oraMax, IProjectObserver client)
        {
            try
            {
                DateTime data = excursie.oraPlecare;
                DateTime dataMin = data.AddHours(oraMin);
                DateTime dataMax = data.AddHours(oraMax);

                List<Excursie> excursii;
                excursii = (List<Excursie>)excursieRepo.findExcursiiLaLocSiOra(excursie.obiectivVizitat, dataMin, dataMax);

                return excursii.ToArray();
            }
            catch(Exception e) 
            {
                throw new ProjectException("Error "+e);
            }
        }

        public void rezerva(Bilet bilet, IProjectObserver client)
        {
            try
            {
                if(bilet.numarPersoane <= excursieRepo.checkLocuriDisponibile(bilet.idExcursie))
                {
                    biletRepo.add(bilet);
                    excursieRepo.updateLocuriDisponibile(bilet.idExcursie, bilet.numarPersoane);
                    ticketBought();
                }
                else
                {
                    throw new ProjectException("Not enough seats");
                }
            }catch(Exception e) { 
                throw new ProjectException();
            }
        }

        private void ticketBought()
        {
            foreach(var client in loggedClients.Values)
            {
                try
                {
                    client.ticketBought();
                }
                catch (ProjectException e)
                {
                    Console.WriteLine("Error notifying agency " + e);
                }
            }
        }
    }
}
