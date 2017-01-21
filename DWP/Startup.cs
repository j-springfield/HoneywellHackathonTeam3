using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(DWP.Startup))]
namespace DWP
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
