using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
//using Microsoft.Data.Sqlite;

namespace persistence
{
    public class SqliteConnectionFactory : ConnectionFactory
    {

        public override IDbConnection createConnection(IDictionary<string, string> props)
        {
            //String connectionString = "Data Source=C:/Users/RoscaMitrut/Desktop/SEM4/MPP/Labs/ProiectCSharp/AgentieTurism/bin/Debug/agentieturism.db";
            String connectionString = props["ConnectionString"];

            return new SQLiteConnection(connectionString);
        }
    }
}
