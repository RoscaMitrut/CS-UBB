using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using log4net;
using model;

namespace persistence
{
    public class ExcursieRepositoryDb : IExcursieRepository
    {
        private static readonly ILog log = LogManager.GetLogger("ExcursieRepositoryDb");

        IDictionary<String, string> props;

        public ExcursieRepositoryDb(IDictionary<String, string> props)
        {
            log.Info("Creating ExcursieRepositoryDb");
            this.props = props;
        }

        public Excursie add(Excursie elem)
        {
            var con = DBUtils.getConnection(props);
            log.Info("enter excursie add");
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Excursii (obiectivVizitat, oraPlecare, firmaTransport, pret, locuriDisponibile) values (@obiectivVizitat, @oraPlecare, @firmaTransport, @pret, @locuriDisponibile)";

                var paramObiectivVizitat = comm.CreateParameter();
                paramObiectivVizitat.ParameterName = "@obiectivVizitat";
                paramObiectivVizitat.Value = elem.obiectivVizitat;
                comm.Parameters.Add(paramObiectivVizitat);

                var paramOraPlecare = comm.CreateParameter();
                paramOraPlecare.ParameterName = "@oraPlecare";
                paramOraPlecare.Value = elem.oraPlecare;
                comm.Parameters.Add(paramOraPlecare);

                var paramFirmaTransport = comm.CreateParameter();
                paramFirmaTransport.ParameterName = "@firmaTransport";
                paramFirmaTransport.Value = elem.firmaTransport;
                comm.Parameters.Add(paramFirmaTransport);

                var paramPret = comm.CreateParameter();
                paramPret.ParameterName = "@pret";
                paramPret.Value = elem.pret;
                comm.Parameters.Add(paramPret);

                var paramLocuriDisponibile = comm.CreateParameter();
                paramLocuriDisponibile.ParameterName = "@locuriDisponibile";
                paramLocuriDisponibile.Value = elem.locuriDisponibile;
                comm.Parameters.Add(paramLocuriDisponibile);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.Info("Not added");
                }
            }
            log.Info("exit excursie add");
            return elem;
        }
        public Excursie update(int integer, Excursie elem)
        {
            var con = DBUtils.getConnection(props);
            log.Info("enter excursie update");
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Excursii set obiectivVizitat=@obiectivVizitat, oraPlecare=@oraPlecare, firmaTransport=@firmaTransport, pret=@pret, locuriDisponibile=@locuriDisponibile WHERE id = @id";

                var paramObiectivVizitat = comm.CreateParameter();
                paramObiectivVizitat.ParameterName = "@obiectivVizitat";
                paramObiectivVizitat.Value = elem.obiectivVizitat;
                comm.Parameters.Add(paramObiectivVizitat);

                var paramOraPlecare = comm.CreateParameter();
                paramOraPlecare.ParameterName = "@oraPlecare";
                paramOraPlecare.Value = elem.oraPlecare;
                comm.Parameters.Add(paramOraPlecare);

                var paramFirmaTransport = comm.CreateParameter();
                paramFirmaTransport.ParameterName = "@firmaTransport";
                paramFirmaTransport.Value = elem.firmaTransport;
                comm.Parameters.Add(paramFirmaTransport);

                var paramPret = comm.CreateParameter();
                paramPret.ParameterName = "@pret";
                paramPret.Value = elem.pret;
                comm.Parameters.Add(paramPret);

                var paramLocuriDisponibile = comm.CreateParameter();
                paramLocuriDisponibile.ParameterName = "@locuriDisponibile";
                paramLocuriDisponibile.Value = elem.locuriDisponibile;
                comm.Parameters.Add(paramLocuriDisponibile);

                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = integer;
                comm.Parameters.Add(paramId);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.Info("Not updated");
                }
            }
            log.Info("exit excursie update");
            return elem;
        }

        public IEnumerable<Excursie> findAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.Info("enter excursie findall");
            IList<Excursie> excursii = new List<Excursie>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,obiectivVizitat, oraPlecare, firmaTransport, pret, locuriDisponibile from Excursii";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        String obiectivVizitat = dataR.GetString(1);
                        DateTime oraPlecare = dataR.GetDateTime(2);
                        String firmaTransport = dataR.GetString(3);
                        Double pret = dataR.GetDouble(4);
                        int locuriDisponibile = dataR.GetInt32(5);
                        Excursie excursie = new Excursie(obiectivVizitat, oraPlecare, firmaTransport, pret, locuriDisponibile);
                        excursie.setId(id);
                        excursii.Add(excursie);
                    }
                }
            }
            log.Info("exit excursie findall");
            return excursii;
        }
        public Excursie findOne(int id)
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.Info("enter excursie findone");
            Excursie excursie = new Excursie();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,obiectivVizitat, oraPlecare, firmaTransport, pret, locuriDisponibile from Excursii WHERE id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id_nou = dataR.GetInt32(0);
                        String obiectivVizitat = dataR.GetString(1);
                        DateTime oraPlecare = dataR.GetDateTime(2);
                        String firmaTransport = dataR.GetString(3);
                        Double pret = dataR.GetDouble(4);
                        int locuriDisponibile = dataR.GetInt32(5);

                        excursie.obiectivVizitat = obiectivVizitat;
                        excursie.oraPlecare = oraPlecare;
                        excursie.firmaTransport = firmaTransport;
                        excursie.pret = pret;
                        excursie.locuriDisponibile = locuriDisponibile;
                        excursie.setId(id_nou);
                    }
                }
            }
            log.Info("exit excursie findone");
            return excursie;
        }

        public IEnumerable<Excursie> findExcursiiLaLocSiOra(String obiectivVizitat, DateTime oraMin, DateTime oraMax)
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.Info("enter excursie findExcursiiLaLocSiOra");
            IList<Excursie> excursii = new List<Excursie>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,obiectivVizitat, oraPlecare, firmaTransport, pret, locuriDisponibile from Excursii WHERE oraPlecare BETWEEN @oraMinima AND @oraMaxima AND obiectivVizitat=@obiectiv";

                var paramObiectivVizitat = comm.CreateParameter();
                paramObiectivVizitat.ParameterName = "@obiectiv";
                paramObiectivVizitat.Value = obiectivVizitat;
                comm.Parameters.Add(paramObiectivVizitat);

                var paramOraMin = comm.CreateParameter();
                paramOraMin.ParameterName = "@oraMinima";
                paramOraMin.Value = oraMin;
                comm.Parameters.Add(paramOraMin);

                var paramOraMax = comm.CreateParameter();
                paramOraMax.ParameterName = "@oraMaxima";
                paramOraMax.Value = oraMax;
                comm.Parameters.Add(paramOraMax);
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        String obiectivVizitatGasit = dataR.GetString(1);
                        DateTime oraPlecare = dataR.GetDateTime(2);
                        String firmaTransport = dataR.GetString(3);
                        Double pret = dataR.GetDouble(4);
                        int locuriDisponibile = dataR.GetInt32(5);
                        Excursie excursie = new Excursie(obiectivVizitatGasit, oraPlecare, firmaTransport, pret, locuriDisponibile);
                        excursie.setId(id);
                        excursii.Add(excursie);
                    }
                }
            }
            log.Info("exit excursie findExcursiiLaLocSiOra");
            return excursii;
        }


        public void updateLocuriDisponibile(int id, int numarLocuriDorite)
        {
            var con = DBUtils.getConnection(props);
            log.Info("enter excursie update");
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Excursii set locuriDisponibile=locuriDisponibile-@numarLocuriDorite WHERE id = @id";

                var paramLocuriDorite = comm.CreateParameter();
                paramLocuriDorite.ParameterName = "@numarLocuriDorite";
                paramLocuriDorite.Value = numarLocuriDorite;
                comm.Parameters.Add(paramLocuriDorite);

                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.Info("Not updated");
                }
            }
            log.Info("exit excursie update");
        }

        public int checkLocuriDisponibile(int id)
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.Info("enter excursie findall");
            int nrLocuri = 0;
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select locuriDisponibile from Excursii where id=@id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int locuriDisponibile = dataR.GetInt32(0);
                        nrLocuri = locuriDisponibile;
                    }
                }
            }
            log.Info("exit excursie findall");
            return nrLocuri;
        }

    }
}
