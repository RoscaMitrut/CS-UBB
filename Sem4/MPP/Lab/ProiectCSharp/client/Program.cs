using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using networking;
using services;
using protobuf;

namespace client
{
    static class Program
    {
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            IProjectServices server = new ProjectServerProxy("127.0.0.1", 5555);
            ProjectClientCtrl ctrl = new ProjectClientCtrl(server);
            Login win = new Login(ctrl);

            //PLACEHOLDER  TAKE A LOOK ON HOW TO CONNECT THEM TO EACHOTHER

            //IProjectServices server = new ProtoProjectProxy();
            //ProjectClientCtrl ctrl = new 
            
           
            
            
            
            Application.Run(win);
        }
    }
}

