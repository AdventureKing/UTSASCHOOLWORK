using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(BackspaceBoysUI.Startup))]
namespace BackspaceBoysUI
{
    public partial class Startup {
        public void Configuration(IAppBuilder app) {
            ConfigureAuth(app);
        }
    }
}
