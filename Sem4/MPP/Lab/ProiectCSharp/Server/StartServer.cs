using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using log4net.Config;
using model;
using networking;
using persistence;
using services;
using protobuf;
using System.Windows.Forms.VisualStyles;

namespace Server
{
    class StartServer
    {
        static void Main(string[] args)
        {
            BasicConfigurator.Configure();
            //XmlConfigurator.Configure(new FileInfo("/LoggerConfig.xml"));

            IDictionary<String, String> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("agentieturism"));

            UserRepositoryDb userRepo = new UserRepositoryDb(props);
            ExcursieRepositoryDb excursieRepo = new ExcursieRepositoryDb(props);
            BiletRepositoryDb biletRepo = new BiletRepositoryDb(props);



            IProjectServices projectServices = new ProjectServicesImpl(userRepo, excursieRepo, biletRepo);


            //SerialProjectServer server = new SerialProjectServer("127.0.0.1", 5555, projectServices);
            ProtoProjectServer server = new ProtoProjectServer("127.0.0.1", 5555, projectServices);

            server.Start();
            Console.WriteLine("Server started ");
            Console.ReadLine();
        }

        static string GetConnectionStringByName(string name) {
            string returnValue = null;
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];
            //MessageBox.Show(settings.ConnectionString);
            //Console.WriteLine(settings.ConnectionString);
            if (settings!=null)
                returnValue=settings.ConnectionString;
            return returnValue;
        }
    }

    public class SerialProjectServer: ConcurrentServer
    {
        private IProjectServices server;
        private ProjectClientWorker worker;

        public SerialProjectServer(string host, int port, IProjectServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialProjectServer");
        }

        protected override Thread createWorker(TcpClient client)
        {
            worker = new ProjectClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }

    public class ProtoProjectServer: ConcurrentServer
    {
        private IProjectServices server;
        private ProtoProjectWorker worker;
        public ProtoProjectServer(string host, int port, IProjectServices server)
            : base(host, port)
        {
            this.server = server;
            Console.WriteLine("ProtoProjectServer");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ProtoProjectWorker(server, client);
            return new Thread (new ThreadStart(worker.run));
        }
    }
}
