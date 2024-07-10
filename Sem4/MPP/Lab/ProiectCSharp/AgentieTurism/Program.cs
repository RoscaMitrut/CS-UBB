/*
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using AgentieTurism.service;
using log4net.Config;
using log4net;

namespace AgentieTurism
{
    internal static class Program
    {
        [STAThread]
        static void Main(string[] args)
        {
            //BasicConfigurator.Configure();
            XmlConfigurator.Configure(new FileInfo("/LoggerConfig.xml"));

            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("agentieturism"));

            UserDBRepository userRepo = new UserDBRepository(props);
            ExcursieDBRepository excursieRepo = new ExcursieDBRepository(props);
            BiletDBRepository biletRepo = new BiletDBRepository(props);


            Service service = new Service(userRepo,excursieRepo,biletRepo);

            ApplicationConfiguration.Initialize();
            Login login = new Login();
            login.SetService(service);
            Application.Run(login);
        }
        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }
}
*/