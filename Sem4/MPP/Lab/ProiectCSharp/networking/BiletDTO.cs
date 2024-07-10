using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;

namespace networking
{
    [Serializable]
    public class BiletDTO
    {
        public int numarPersoane { get; set; }
        public String numeClient { get; set; }
        public int numarTelefonClient { get; set; }
        public int idExcursie { get; set; }
        public int id { get; set; }

        public BiletDTO(int numarPersoane, String numeClient, int numarTelefonClient, int idExcursie, int id)
        {
            this.id = id;
            this.idExcursie = idExcursie;
            this.numeClient = numeClient;
            this.numarTelefonClient = numarTelefonClient;
            this.numarPersoane = numarPersoane;
        }
        public BiletDTO(Bilet bilet)
        {
            this.id = bilet.id;
            this.idExcursie = bilet.idExcursie;
            this.numeClient = bilet.numeClient;
            this.numarTelefonClient = bilet.numarTelefonClient;
            this.numarPersoane = bilet.numarPersoane;
        }
    }
}
