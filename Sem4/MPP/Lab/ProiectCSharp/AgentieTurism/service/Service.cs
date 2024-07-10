/*
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms.VisualStyles;
using AgentieTurism.domain;
using AgentieTurism.repo;

namespace AgentieTurism.service
{
    public class Service
    {
        private UserRepository userRepo;
        private ExcursieRepository excursieRepo;
        private BiletRepository biletRepo;


        public Service(UserRepository userRepo, ExcursieRepository excursieRepo, BiletRepository biletRepo)
        {
            this.userRepo = userRepo;
            this.excursieRepo = excursieRepo;
            this.biletRepo = biletRepo;
        }

        public IEnumerable<Excursie> getExcursii() { return excursieRepo.findAll(); }
        public IEnumerable<Bilet> getBilete() { return biletRepo.findAll(); }
        public IEnumerable<User> getUseri() { return userRepo.findAll(); }

        public User getUser(String username,String password)
        {
            return userRepo.findUser(username, password);
        }

        public Excursie GetExcursie(int id)
        {
            return excursieRepo.findOne(id);
        }

        public IEnumerable<Excursie> getExcursiiLaLocSiOra(String obiectiv, DateTime data, int oraMin, int oraMax)
        {
            DateTime dataMin = data.AddHours(oraMin);
            DateTime dataMax = data.AddHours(oraMax);
            return excursieRepo.findExcursiiLaLocSiOra(obiectiv, dataMin, dataMax);
        }
        public Boolean rezerva(Excursie excursie, String numeClient, int nrTelefon,int nrLocuri)
        {
            if (nrLocuri <= excursie.locuriDisponibile)
            {
                Bilet bilet = new Bilet(nrLocuri, numeClient, nrTelefon, excursie.id);
                biletRepo.add(bilet);
                excursieRepo.updateLocuriDisponibile(excursie.id, nrLocuri);
                return true;
            }
            else
            {
                return false;//THROW
            }
        }
    }
}
*/ 