using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using model;
using Project.Protocol;
using proto = Project.Protocol;


namespace protobuf
{
    static class ProtoUtils
    {
        public static ProjectRequest createLoginRequest(model.User user)
        {
            String id = user.id.ToString();
            proto.User userDTO = new proto.User { Id = id, Password = user.password, Username = user.username };
            ProjectRequest request = new ProjectRequest {Type=ProjectRequest.Types.Type.Login, User=userDTO };
            return request;
        }

        public static ProjectRequest createLogoutRequest(model.User user)
        {
            proto.User userDTO = new proto.User { Id = user.id.ToString(), Password=user.password, Username=user.username };
            ProjectRequest request = new ProjectRequest { Type = ProjectRequest.Types.Type.Logout, User = userDTO };
            return request;
        }

        public static ProjectRequest createGetTripsRequest()
        {
            ProjectRequest request = new ProjectRequest { Type = ProjectRequest.Types.Type.GetTrips };
            return request;
        }

        public static ProjectRequest createGetFilteredTripsRequest(model.Excursie excursie, int oraMin, int oraMax)
        {
            proto.Excursie excursieDTO = new proto.Excursie { FirmaTransport = excursie.firmaTransport, Id = excursie.id.ToString(), LocuriDisponibile = excursie.locuriDisponibile.ToString(), ObiectivVizitat = excursie.obiectivVizitat, OraPlecare = excursie.oraPlecare.ToString(), Pret = excursie.pret.ToString() };
            ProjectRequest request = new ProjectRequest { Type = ProjectRequest.Types.Type.GetFilteredTrips, Excursie = excursieDTO, Oramin = oraMin.ToString(), Oramax = oraMax.ToString() };
            return request;
        }

        public static ProjectRequest createBuyTicketRequest(model.Bilet bilet)
        {
            proto.Bilet biletDTO = new proto.Bilet { Id = bilet.id.ToString(), IdExcursie=bilet.idExcursie.ToString(), NumarPersoane=bilet.numarPersoane.ToString(), NumarTelefonClient=bilet.numarTelefonClient.ToString(), NumeClient=bilet.numeClient };
            ProjectRequest request = new ProjectRequest { Type = ProjectRequest.Types.Type.BuyTicket, Bilet = biletDTO };
            return request;
        }


        public static ProjectResponse createOkResponse()
        {
            ProjectResponse response = new ProjectResponse { Type = ProjectResponse.Types.Type.Ok };
            return response;
        }

        public static ProjectResponse createErrorResponse(String text)
        {
            ProjectResponse response = new ProjectResponse
            {
                Type = ProjectResponse.Types.Type.Error,
                ErrorMessage = text
            };
            return response;
        }

        public static ProjectResponse createTripsResponse(model.Excursie[] excursii)
        {
            ProjectResponse response = new ProjectResponse
            {
                Type = ProjectResponse.Types.Type.Trips
            };
            foreach (model.Excursie excursie in excursii)
            {
                proto.Excursie excursieDTO = new proto.Excursie
                {
                    Id = excursie.id.ToString(),
                    FirmaTransport = excursie.firmaTransport,
                    LocuriDisponibile = excursie.locuriDisponibile.ToString(),
                    ObiectivVizitat = excursie.obiectivVizitat,
                    OraPlecare = excursie.oraPlecare.ToString(),
                    Pret = excursie.pret.ToString()
                };
                response.Excursii.Add(excursieDTO);
            }
            return response;

        }

        public static ProjectResponse createFilteredTripsResponse(model.Excursie[] excursii)
        {
            ProjectResponse response = new ProjectResponse
            {
                Type = ProjectResponse.Types.Type.FilteredTrips
            };
            foreach (model.Excursie excursie in excursii)
            {
                proto.Excursie excursieDTO = new proto.Excursie
                {
                    Id = excursie.id.ToString(),
                    FirmaTransport = excursie.firmaTransport,
                    LocuriDisponibile = excursie.locuriDisponibile.ToString(),
                    ObiectivVizitat = excursie.obiectivVizitat,
                    OraPlecare = excursie.oraPlecare.ToString(),
                    Pret = excursie.pret.ToString()
                };
                response.Excursii.Add(excursieDTO);
            }
            return response;

        }

        public static ProjectResponse createBuyTicketResponse()
        {
            ProjectResponse response = new ProjectResponse
            {
                Type = ProjectResponse.Types.Type.BoughtTicket
            };
            return response;
        }

        public static String getError(proto.ProjectResponse response)
        {
            String errorMsg = response.ErrorMessage;
            return errorMsg;
        }

        //USELESS? PLACEHOLDER
        public static model.User getUser(proto.ProjectResponse response)
        {
            model.User user = new model.User();
            user.setId(int.Parse(response.User.Id));
            return user;

        }

        //USELESS? PLACEHOLDER
        public static model.Bilet getBilet(proto.ProjectResponse response)
        {
            int nrPers = int.Parse(response.Bilet.NumarPersoane);
            String numeClient = response.Bilet.NumeClient;
            int nrTel = int.Parse(response.Bilet.NumarTelefonClient);
            int idEx = int.Parse(response.Bilet.IdExcursie);
            int id = int.Parse(response.Bilet.Id);

            model.Bilet bilet = new model.Bilet(nrPers,numeClient,nrTel,idEx);
            bilet.setId(id);
            return bilet;
        }

        public static model.Excursie[] getExcursii(proto.ProjectResponse response)
        {
            model.Excursie[] excursii = new model.Excursie[response.Excursii.Count];
            for (int i = 0;i<response.Excursii.Count;i++)
            {
                String obiectiv = response.Excursii[i].ObiectivVizitat;
                DateTime oraPlecare = DateTime.Parse(response.Excursii[i].OraPlecare);
                String firmaTransport = response.Excursii[i].FirmaTransport;
                Double pret = Double.Parse(response.Excursii[i].Pret);
                int locuriDisponibile = int.Parse(response.Excursii[i].LocuriDisponibile);
                int id = int.Parse(response.Excursii[i].Id);

                model.Excursie excursie = new model.Excursie(obiectiv,oraPlecare,firmaTransport,pret,locuriDisponibile);
                excursie.setId(id);
                excursii[i] = excursie;
            }
            return excursii;
        }

        public static model.User getUser(proto.ProjectRequest request)
        {
            model.User user = new model.User();
            user.username = request.User.Username;
            user.password = request.User.Password;
            user.setId(int.Parse(request.User.Id));
            return user;
        }

        public static model.Excursie getExcursie(proto.ProjectRequest request)
        {
            model.Excursie excursie = new model.Excursie();
            excursie.setId(int.Parse(request.Excursie.Id));
            excursie.pret = Double.Parse(request.Excursie.Pret);
            excursie.firmaTransport = request.Excursie.FirmaTransport;
            excursie.oraPlecare = DateTime.Parse(request.Excursie.OraPlecare);
            excursie.obiectivVizitat = request.Excursie.ObiectivVizitat;
            excursie.locuriDisponibile = int.Parse(request.Excursie.LocuriDisponibile);

            return excursie;
        }

        public static model.Excursie GetExcursieFiltrare(proto.ProjectRequest request)
        {
            model.Excursie excursie = new model.Excursie();
            excursie.obiectivVizitat = request.Excursie.ObiectivVizitat;
            excursie.oraPlecare = DateTime.Parse(request.Excursie.OraPlecare);

            return excursie;
        }

        public static int getOraMin(proto.ProjectRequest request)
        {
            return int.Parse(request.Oramin);
        }

        public static int getOraMax(proto.ProjectRequest request)
        {
            return int.Parse(request.Oramax);
        }

        public static model.Bilet getBilet(proto.ProjectRequest request)
        {
            model.Bilet bilet = new model.Bilet();
            bilet.setId(int.Parse(request.Bilet.Id));
            bilet.numarPersoane = int.Parse(request.Bilet.NumarPersoane);
            bilet.idExcursie = int.Parse(request.Bilet.IdExcursie);
            bilet.numeClient = request.Bilet.NumeClient;
            bilet.numarTelefonClient = int.Parse(request.Bilet.NumarTelefonClient);

            return bilet;
        }
    }
}
