using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using model;
using log4net;
using System.Data;

namespace persistence
{
    public class BiletRepositoryDb:IBiletRepository
    {
        private static readonly ILog log = LogManager.GetLogger("BiletRepositoryDb");

        IDictionary<String, string> props;

        public BiletRepositoryDb(IDictionary<String, string> props)
        {
            log.Info("Creating BiletRepositoryDb");
            this.props = props;
        }

        public Bilet add(Bilet elem)
        {
            var con = DBUtils.getConnection(props);
            log.Info("enter bilet add");
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Bilete (numarPersoane, numeClient, numarTelefonClient, idExcursie) values (@numarPersoane,@numeClient,@numarTelefonClient,@idExcursie)";

                var paramNumarPersoane = comm.CreateParameter();
                paramNumarPersoane.ParameterName = "@numarPersoane";
                paramNumarPersoane.Value = elem.numarPersoane;
                comm.Parameters.Add(paramNumarPersoane);

                var paramNumeClient = comm.CreateParameter();
                paramNumeClient.ParameterName = "@numeClient";
                paramNumeClient.Value = elem.numeClient;
                comm.Parameters.Add(paramNumeClient);

                var paramNumarTelefonClient = comm.CreateParameter();
                paramNumarTelefonClient.ParameterName = "@numarTelefonClient";
                paramNumarTelefonClient.Value = elem.numarTelefonClient;
                comm.Parameters.Add(paramNumarTelefonClient);

                var paramIdExcursie = comm.CreateParameter();
                paramIdExcursie.ParameterName = "@idExcursie";
                paramIdExcursie.Value = elem.idExcursie;
                comm.Parameters.Add(paramIdExcursie);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                {
                    log.Info("Not added");
                }
            }
            log.Info("exit bilet add");
            return elem;
        }
        public Bilet update(int integer, Bilet elem)
        {
            var con = DBUtils.getConnection(props);
            log.Info("enter bilet update");
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "update Bilete set numarPersoane=@numarPersoane, numeClient=@numeClient, numarTelefonClient=@numarTelefonClient, idExcursie=@idExcursie WHERE id = @id";

                var paramNumarPersoane = comm.CreateParameter();
                paramNumarPersoane.ParameterName = "@numarPersoane";
                paramNumarPersoane.Value = elem.numarPersoane;
                comm.Parameters.Add(paramNumarPersoane);

                var paramNumeClient = comm.CreateParameter();
                paramNumeClient.ParameterName = "@numeClient";
                paramNumeClient.Value = elem.numeClient;
                comm.Parameters.Add(paramNumeClient);

                var paramNumarTelefonClient = comm.CreateParameter();
                paramNumarTelefonClient.ParameterName = "@numarTelefonClient";
                paramNumarTelefonClient.Value = elem.numarTelefonClient;
                comm.Parameters.Add(paramNumarTelefonClient);

                var paramIdExcursie = comm.CreateParameter();
                paramIdExcursie.ParameterName = "@idExcursie";
                paramIdExcursie.Value = elem.idExcursie;
                comm.Parameters.Add(paramIdExcursie);

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
            log.Info("exit bilet update");
            return elem;
        }

        public IEnumerable<Bilet> findAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            log.Info("enter bilet findall");
            IList<Bilet> bilete = new List<Bilet>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id,numarPersoane, numeClient, numarTelefonClient, idExcursie from Bilete";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        int id = dataR.GetInt32(0);
                        int numarPersoane = dataR.GetInt32(1);
                        String numeClient = dataR.GetString(2);
                        int numarTelefonClient = dataR.GetInt32(3);
                        int idExcursie = dataR.GetInt32(4);
                        Bilet bilet = new Bilet(numarPersoane, numeClient, numarTelefonClient, idExcursie);
                        bilet.setId(id);
                        bilete.Add(bilet);
                    }
                }
            }
            log.Info("exit bilet findall");
            return bilete;
        }
    }
}
