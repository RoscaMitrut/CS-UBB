using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace model
{
    public class Excursie : Entity<int>
    {
        public String obiectivVizitat { get; set; }
        public DateTime oraPlecare { get; set; }
        public String firmaTransport { get; set; }
        public Double pret { get; set; }
        public int locuriDisponibile { get; set; }

        public int id { get; set; }

        public Excursie(string obiectivVizitat, DateTime oraPlecare, string firmaTransport, double pret, int locuriDisponibile)
        {
            this.obiectivVizitat = obiectivVizitat;
            this.oraPlecare = oraPlecare;
            this.firmaTransport = firmaTransport;
            this.pret = pret;
            this.locuriDisponibile = locuriDisponibile;
        }

        public Excursie()
        {
            this.obiectivVizitat = null;
            this.oraPlecare = DateTime.MinValue;
            this.firmaTransport = null;
            this.pret = 0;
            this.locuriDisponibile = 0;
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
