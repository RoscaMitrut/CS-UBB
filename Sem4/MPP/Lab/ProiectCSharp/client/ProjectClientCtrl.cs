using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using model;
using services;

namespace client
{
    public class ProjectClientCtrl : IProjectObserver
    {
        public event EventHandler<ProjectEventArgs> updateEvent;//ctrl calls it when it recieved an update
        private readonly IProjectServices server;
        private User currentUser;

        public ProjectClientCtrl(IProjectServices server)
        {
            this.server = server;
            currentUser = null;
        }

        public void login(string username, string password)
        {
        /*
            User usr = new User();
            usr.username = username;
            usr.password = password;
            server.login(usr, this);
            Console.WriteLine("Login succeeded ");
            currentUser = usr;
            Console.WriteLine("Current user {0}", usr.username);
        */
            User usr = new User(username, password);
            server.login(usr, this);
            Console.WriteLine("Login succeeded ");
            currentUser = usr;
            Console.WriteLine("Current user {0}", usr.username);

        }

        public void logout()
        {
            Console.WriteLine("Logout ");
            server.logout(currentUser,this);
            currentUser = null;
        }
        public void ticketBought()
        {
            ProjectEventArgs projectEvent = new ProjectEventArgs(ProjectEvent.Booked, new Object());
            OnUserEvent(projectEvent);
        }

        public List<Excursie> getExcursii()
        {
            return server.getExcursii(this).OfType<Excursie>().ToList();
        }

        public List<Excursie> getExcursiiFiltrate(String obiectiv,DateTime data, int oraMin, int oraMax)
        {
            Excursie ex = new Excursie();
            ex.obiectivVizitat = obiectiv;
            ex.oraPlecare = data;
            return server.getExcursiiLaLocSiOra(ex,oraMin,oraMax,this).OfType<Excursie>().ToList();
        }

        public Excursie getExcursie(int id)
        {
            Excursie[] excursii = server.getExcursii(this);
            foreach(Excursie excur in excursii)
            {
                if(excur.id == id) return excur;
            }
            throw new Exception("Error");
        }

        public void rezervare(Excursie excursie, String nume, int nrTel, int nrLocuri)
        {
            //MessageBox.Show(nrLocuri + "     " + excursie.locuriDisponibile);
            //Console.WriteLine(nrLocuri);
            //Console.WriteLine(excursie.locuriDisponibile);
            if(nrLocuri <= excursie.locuriDisponibile)
            {
                Bilet bilet = new Bilet(nrLocuri, nume, nrTel, excursie.id);
                server.rezerva(bilet, this);
            }
            else
            {
                throw new Exception("Not enough seats");
            }
        }

        protected virtual void OnUserEvent(ProjectEventArgs e)
        {
            if (updateEvent == null) return;
            updateEvent(this, e);
            Console.WriteLine("Update Event called");
        }
    }
}
