using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace networking
{
    public interface Request{}

    [Serializable]
    public class LoginRequest : Request
    {
        public UserDTO userDTO { get; set; }
        public LoginRequest(UserDTO userDTO) 
        {
            this.userDTO = userDTO;
        }
    }

    [Serializable]
    public class LogoutRequest : Request
    {
        public UserDTO userDTO { get; set; }
        public LogoutRequest(UserDTO userDTO)
        {
            this.userDTO = userDTO;
        }
    }

    [Serializable]
    public class GetTripsRequest : Request
    {
        public GetTripsRequest()
        {
        }
    }

    [Serializable]
    public class GetFilteredTripsRequest : Request
    {
        public ExcursieDTO excursieDTO { get; set; }
        public int oramin { get; set; }
        public int oramax { get; set; }
        public GetFilteredTripsRequest(ExcursieDTO excursieDTO,int oramin,int oramax)
        {
            this.excursieDTO = excursieDTO;
            this.oramin = oramin;
            this.oramax = oramax;
        }
    }

    [Serializable]
    public class BuyTicketRequest : Request
    {
        public BiletDTO biletDTO { get; set; }
        public BuyTicketRequest(BiletDTO biletDTO)
        {
            this.biletDTO = biletDTO;
        }
    }
}
