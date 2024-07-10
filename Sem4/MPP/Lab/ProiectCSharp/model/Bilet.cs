using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace model
{
    public class Bilet : Entity<int>
    {
        public int numarPersoane { get; set; }
        public String numeClient { get; set; }
        public int numarTelefonClient { get; set; }
        public int idExcursie { get; set; }

        public int id;

        public Bilet(int numarPersoane, string numeClient, int numarTelefonClient, int idExcursie)
        {
            this.numarPersoane = numarPersoane;
            this.numeClient = numeClient;
            this.numarTelefonClient = numarTelefonClient;
            this.idExcursie = idExcursie;
        }

        public Bilet()
        {
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return this.id;
        }
    }
}
