using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;

namespace persistence
{
    public interface IExcursieRepository : IRepository<int, Excursie>
    {
        IEnumerable<Excursie> findExcursiiLaLocSiOra(String obiectivVizitat, DateTime oraMin, DateTime oraMax);

        void updateLocuriDisponibile(int id, int numarLocuriDorite);

        int checkLocuriDisponibile(int id);

        Excursie findOne(int id);
    }
}
