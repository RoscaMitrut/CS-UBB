using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace networking
{
    public interface Response
    {
    }

    [Serializable]
    public class OkResponse : Response { }

    [Serializable]
    public class ErrorResponse:Response {
        private string message;
        public ErrorResponse(string message)
        {
            this.message = message;
        }
        public virtual string Message
        {
            get
            {
                return message;
            }
        }
    }

    [Serializable]
    public class TripsResponse : Response 
    {
        public ExcursieDTO[] excursii { get; set; }
        public TripsResponse(ExcursieDTO[] excursii)
        {
            this.excursii = excursii;
        }
    }

    [Serializable]
    public class BoughtTicketResponse : Response { }
}
