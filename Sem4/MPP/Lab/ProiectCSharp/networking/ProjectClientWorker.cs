using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using model;
using Newtonsoft.Json;
using services;

namespace networking
{
    public class ProjectClientWorker : IProjectObserver
    {
        private IProjectServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public ProjectClientWorker(IProjectServices server,TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e) 
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public void ticketBought()
        {
            sendResponse(new BoughtTicketResponse());
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("Sending response " + response);
            lock (stream)
            {
                formatter.Serialize(stream, response);
                stream.Flush();
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    object request = formatter.Deserialize(stream);
                    object response = handleRequest((Request)request);
                    if (response != null)
                    {
                        sendResponse((Response)response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        private Response handleRequest(Request request)
        {
            Response response = null;

            if (request is LoginRequest)
            {
                Console.WriteLine("Login request");
                LoginRequest loginRequest = (LoginRequest)request;
                UserDTO userdto = loginRequest.userDTO;
                User user = DTOUtils.fromDTO(userdto);
                try
                {
                    lock (server)
                    {
                        server.login(user, this);
                    }
                    return new OkResponse();
                }
                catch (ProjectException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }


            if (request is LogoutRequest)
            {
                Console.WriteLine("Logout request");
                LogoutRequest logoutRequest = (LogoutRequest)request;
                UserDTO userdto = logoutRequest.userDTO;
                User user = DTOUtils.fromDTO(userdto);
                try
                {
                    lock (server)
                    {
                        server.logout(user, this);
                    }
                    connected = false;
                    return new OkResponse();
                }
                catch (ProjectException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }


            if (request is GetTripsRequest)
            {
                Console.WriteLine("GetTrips request");
                GetTripsRequest trips = (GetTripsRequest)request;
                try
                {
                    Excursie[] excursii;
                    lock (server)
                    {
                        excursii = server.getExcursii(this);
                    }
                    ExcursieDTO[] excursiidto = DTOUtils.getDTO(excursii);

                    return new TripsResponse(excursiidto);
                }catch (ProjectException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }


            if (request is GetFilteredTripsRequest)
            {
                Console.WriteLine("GetFilteredTrips request");
                GetFilteredTripsRequest trips = ( GetFilteredTripsRequest)request;
                ExcursieDTO excdto = trips.excursieDTO;
                Excursie exc = DTOUtils.fromDTO(excdto);
                int oramin = trips.oramin;
                int oramax = trips.oramax;
                try
                {
                    Excursie[] excursii;
                    lock (server)
                    {
                        excursii = server.getExcursiiLaLocSiOra(exc, oramin, oramax, this);
                    }
                    ExcursieDTO[] excursiidto = DTOUtils.getDTO(excursii);
                    return new TripsResponse(excursiidto);
                }
                catch (ProjectException e)
                {
                    return new ErrorResponse (e.Message);
                }
            }


            if (request is BuyTicketRequest)
            {
                Console.WriteLine("BuyTicket request");
                BuyTicketRequest buyRequest = ( BuyTicketRequest)request;
                Bilet bilet = DTOUtils.fromDTO(buyRequest.biletDTO);
                try
                {
                    lock (server)
                    {
                        server.rezerva(bilet, this);
                    }
                    ticketBought();
                    return new OkResponse();
                }
                catch(ProjectException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }
            return response;
        }

    }
}
