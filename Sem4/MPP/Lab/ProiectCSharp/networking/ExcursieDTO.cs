using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;

namespace networking
{
    [Serializable]
    public class ExcursieDTO
    {
        public String obiectivVizitat { get; set; }
        public String oraPlecare {get; set; }
        public String firmaTransport { get; set; }
        public Double pret { get; set; }
        public int locuriDisponibile { get; set; }
        public int id { get; set; }

        public ExcursieDTO(Excursie excursie)
        {
            this.id = excursie.id;
            this.obiectivVizitat = excursie.obiectivVizitat;
            this.oraPlecare = excursie.oraPlecare.ToString();
            this.firmaTransport = excursie.firmaTransport;
            this.pret = excursie.pret;
            this.locuriDisponibile = excursie.locuriDisponibile;
        }
        public ExcursieDTO(string obiectivVizitat, DateTime oraPlecare, string firmaTransport, double pret, int locuriDisponibile, int id)
        {
            this.obiectivVizitat = obiectivVizitat;
            this.oraPlecare = oraPlecare.ToString();
            this.firmaTransport = firmaTransport;
            this.pret = pret;
            this.locuriDisponibile = locuriDisponibile;
            this.id = id;
        }
    }
}
