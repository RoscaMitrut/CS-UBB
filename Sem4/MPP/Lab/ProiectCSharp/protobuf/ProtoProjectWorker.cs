using System;
using System.Threading;
using System.Net.Sockets;
using Google.Protobuf;
using services;
using Project.Protocol;

namespace protobuf
{
    public class ProtoProjectWorker:IProjectObserver
    {
        private IProjectServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private volatile bool connected;

        public ProtoProjectWorker(IProjectServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                connected = true;
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected) {
                try
                {

                    /*
                    // Read bytes from the NetworkStream
                    byte[] buffer = new byte[4096]; // Assuming a buffer size of 4096 bytes
                    StringBuilder stringBuilder = new StringBuilder();
                    int bytesRead;
                    do
                    {
                        bytesRead = stream.Read(buffer, 0, buffer.Length);
                        stringBuilder.Append(Encoding.UTF8.GetString(buffer, 0, bytesRead));
                    } while (bytesRead == buffer.Length);
                    // Convert the bytes to a string using UTF-8 encoding
                    string receivedData = stringBuilder.ToString();
                    // Print the string to the console
                    Console.WriteLine(receivedData);
                    */



                    ProjectRequest request = ProjectRequest.Parser.ParseDelimitedFrom(stream);// PLACEHOLDER WE HAVE BIG AMOUNT OF PROBLEM BECAUSE OF THIS FKING LINE ASJDGNIJADIGNADFINADIFGNIJADFNGIBADFGINADUIGNIUADFNGIUADNGIUBADFIGNADIFJGN
                    ProjectResponse response = handleRequest(request);
                    if(response != null)
                    {
                        sendResponse(response);
                    }
                }
                catch (Exception ex) 
                {
                Console.WriteLine("inceput");
                Console.WriteLine(ex.StackTrace);
                Console.WriteLine("-----");
                Console.WriteLine(ex.Message);
                Console.WriteLine("gata");
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

        public virtual void ticketBought()
        {
            Console.WriteLine("Ticket Bought");
            try
            {
                sendResponse(ProtoUtils.createBuyTicketResponse());
            }
            catch(Exception ex)
            {
                throw new ProjectException("Error " + ex);
            }
        }

        private ProjectResponse handleRequest(ProjectRequest request)
        {
            ProjectResponse response = null;
            ProjectRequest.Types.Type reqType = request.Type;
            switch(reqType)
            {
                case ProjectRequest.Types.Type.Login:
                {
                    Console.WriteLine("Login request ");
                    model.User user = ProtoUtils.getUser(request);
                        try
                        {
                            lock (server)
                            {
                                server.login(user, this);
                            }
                            return ProtoUtils.createOkResponse();
                        }
                        catch(ProjectException e)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                }
                case ProjectRequest.Types.Type.Logout:
                {
                    Console.WriteLine("Logout request ");
                    model.User user = ProtoUtils.getUser(request);
                    try
                    {
                        lock (server)
                        {
                            server.logout(user, this);
                        }
                        connected = false;
                        return ProtoUtils.createOkResponse();
                    }
                    catch (ProjectException e)
                    {
                        return ProtoUtils.createErrorResponse(e.Message);
                    }
            }
                case ProjectRequest.Types.Type.GetTrips:
                {
                    Console.WriteLine("GetTrips request ");
                        try
                        {
                            model.Excursie[] excursii;
                            lock (server)
                            {
                                excursii = server.getExcursii(this);
                            }
                            return ProtoUtils.createTripsResponse(excursii);
                        }
                        catch(ProjectException e)
                        {
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                }
                case ProjectRequest.Types.Type.GetFilteredTrips:
                {
                    Console.WriteLine("GetFilteredTrips request ");
                    model.Excursie excursie = ProtoUtils.GetExcursieFiltrare(request);
                    int oraMin = ProtoUtils.getOraMin(request);
                    int oraMax = ProtoUtils.getOraMax(request);
                        try
                        {
                            model.Excursie[] excursii;
                            lock (server)
                            {
                                excursii = server.getExcursiiLaLocSiOra(excursie, oraMin, oraMax, this);
                            }
                            return ProtoUtils.createFilteredTripsResponse(excursii);
                        }
                        catch(ProjectException e)
                        {
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                }
                case ProjectRequest.Types.Type.BuyTicket:
                {
                    Console.WriteLine("BuyTicket request ");
                    model.Bilet bilet = ProtoUtils.getBilet(request);
                        try
                        {
                            lock (server)
                            {
                                server.rezerva(bilet, this);
                            }
                            return ProtoUtils.createOkResponse();//PLACEHOLDER MAYBE SWITCH TO TICKET BOUGHT RESPONSE
                        }
                        catch(ProjectException e)
                        {
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                }
            }
            return response;
        }

        private void sendResponse(ProjectResponse response)
        {
            Console.WriteLine("Sending response " + response);
            lock (stream)
            {
                response.WriteDelimitedTo(stream);
                stream.Flush();
            }
        }
    }
}
