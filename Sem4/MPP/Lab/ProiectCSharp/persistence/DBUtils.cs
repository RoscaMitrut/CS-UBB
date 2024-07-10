using System;
using System.Collections.Generic;
using System.Data;

namespace persistence
{
    public static class DBUtils
    {

        private static IDbConnection instance = null;


        public static IDbConnection getConnection(IDictionary<String, String> props)
        {
            //Console.WriteLine(props.Values.ToString());
            if (instance == null || instance.State == System.Data.ConnectionState.Closed)
            {
                
                instance = getNewConnection(props);
                instance.Open();
            }
            return instance;
        }

        private static IDbConnection getNewConnection(IDictionary<string, string> props)
        {

            return ConnectionFactory.getInstance().createConnection(props);

        }
    }
}
