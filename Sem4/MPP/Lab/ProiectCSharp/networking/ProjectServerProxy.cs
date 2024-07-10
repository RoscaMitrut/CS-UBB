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
using services;

namespace networking
{
    public class ProjectServerProxy : IProjectServices
    {
        private string host;
        private int port;
        private IProjectObserver client;
        private NetworkStream stream;
        private IFormatter formatter;
        private TcpClient connection;
        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;

        public ProjectServerProxy(string host,int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        public virtual void login(User user, IProjectObserver client)
        {
            initializeConnection();
            UserDTO userdto = DTOUtils.getDTO(user);
            sendRequest(new LoginRequest(userdto));
            Response response = readResponse();
            if(response is OkResponse)
            {
                this.client = client;
                return;
            }
            if(response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                closeConnection();
                throw new ProjectException(err.Message);
            }
        }

        public void logout(User user, IProjectObserver client) {
            UserDTO userdto = DTOUtils.getDTO(user);
            sendRequest(new LogoutRequest(userdto));
            Response response = readResponse();
            closeConnection();
            if(response is ErrorResponse)
            {
                ErrorResponse err = (ErrorResponse)response;
                throw new ProjectException(err.Message);
            }
        }

        private void initializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                startReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void closeConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void sendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }catch(Exception e)
            {
                throw new ProjectException("Error sending object " + e);
            }
        }

        private Response readResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    response = responses.Dequeue();
                }
            }catch(Exception e)
            {
                Console.WriteLine(e.StackTrace) ;
            }
            return response;
        }

        private void startReader()
        {
            Thread tw = new Thread(run);
            tw.Start();
        }
        public virtual void run()
        {
            while(!finished)
            {
                try
                {
                    object response = formatter.Deserialize(stream);
                    Console.WriteLine("response received" + response);
                    if (response is BoughtTicketResponse)
                    {
                        try
                        {
                            client.ticketBought();
                        }
                        catch(ProjectException e)
                        {
                            Console.WriteLine(e.StackTrace);
                        }
                    }
                    else
                    {
                        lock(response)
                        {
                            responses.Enqueue((Response)response);
                        }
                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }
            }
        }

        public Excursie[] getExcursii(IProjectObserver client)
        {
            Request request = new GetTripsRequest();
            sendRequest(request);
            Response response = readResponse();

            if(response is TripsResponse)
            {
                TripsResponse resp = (TripsResponse)response;
                ExcursieDTO[] excursiidto = resp.excursii;
                return DTOUtils.fromDTO(excursiidto);
            }
            throw new ProjectException("Error");
        }

        public Excursie[] getExcursiiLaLocSiOra(Excursie excursie, int oraMin, int oraMax, IProjectObserver client)
        {
            ExcursieDTO excursiedto = DTOUtils.getDTO(excursie);
            Request request = new GetFilteredTripsRequest(excursiedto,oraMin, oraMax);
            sendRequest(request);
            Response response = readResponse();

            if(response is TripsResponse)
            {
                TripsResponse resp = (TripsResponse)response;
                ExcursieDTO[] excursiidto = resp.excursii;
                return DTOUtils.fromDTO(excursiidto);
            }
            throw new ProjectException("Error");
        }

        public void rezerva(Bilet bilet, IProjectObserver client)
        {
            BiletDTO biletdto = DTOUtils.getDTO(bilet);
            Request request = new BuyTicketRequest(biletdto);
            sendRequest(request);
            Response response = readResponse();
            if(response is ErrorResponse)
            {
                ErrorResponse err= (ErrorResponse)response;
                throw new ProjectException(err.Message);
            }
        }
    }
}
