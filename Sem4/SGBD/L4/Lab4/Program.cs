﻿using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Lab4
{
    public class Program
    {
        public static void Main(string[] args)
        {
            string connectionString = GetConnectionStringByName("connection");
            Thread deadlock1 = new Thread(() => { RunProcedure(connectionString, "deadlock_transaction1"); });
            Thread deadlock2 = new Thread(() => { RunProcedure(connectionString, "deadlock_transaction2"); });

            deadlock1.Start();

            deadlock2.Start();
        }

        public static void RunProcedure(string connectionString, string procedureName)
        {
            SqlConnection conn = new SqlConnection(connectionString);
            SqlCommand cmd = new SqlCommand(procedureName, conn);
            cmd.CommandType = System.Data.CommandType.StoredProcedure;
            conn.Open();

            Console.WriteLine($"Started procedure: {procedureName}");
            for (int currentTry = 0; currentTry < 2; currentTry++)
            {
                try
                {
                    cmd.ExecuteNonQuery();
                    Console.WriteLine($"{procedureName} executed successfully after {currentTry} tries.");
                    break;
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"{procedureName} failed: {ex.Message} (try: {currentTry}).");
                //    Console.WriteLine($"{procedureName} failed (try: {currentTry}).");

                }
            }
            conn.Close();
        }

        public static string GetConnectionStringByName(string name)
        {
            string returnValue = null;
            var settings = ConfigurationManager.ConnectionStrings[name];
            if (settings != null)
                returnValue = settings.ConnectionString;
            return returnValue;
        }
    }
}
