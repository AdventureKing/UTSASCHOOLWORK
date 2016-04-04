<%@ Page Title="" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="PopularStreams.aspx.cs" Inherits="BackspaceBoysUI.PopularStreams" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">

     <div class ="jumbotron">
        <h1 style="text-align: center"><strong>Popular Streams</strong></h1>
        <h3 style="text-align: center">The biggest streams on twitch</h3>
     </div>

    <div class ="row">
        <div class ="col-md-4">
            <table class ="table table-striped">   
                <thead>
                    <tr>
                        <th ><label type="text" id="streamTitle1" runat="server" /></th> 
                    </tr>
                </thead>
                <tbody>
                    <td><iframe id="player1" type="text/html" runat="server" width="100%" height="300px"
                    src="" frameborder="0"></iframe></td>
                </tbody>
            </table>
        </div>
        <div class ="col-md-4">
            <table class ="table table-striped">   
                <thead>
                    <tr>
                        <th><label type="text" id="streamTitle2" runat="server" /></th> 
                    </tr>
                </thead>
                <tbody>    
                    <td><iframe id="player2" type="text/html" runat="server" width="100%" height="300px"
                    src="" frameborder="0"></iframe></td>
                </tbody>
            </table>
        </div>
        <div class ="col-md-4"> 
            <table class ="table table-striped">   
                <thead>
                    <tr>
                        <th><label type="text" id="streamTitle3" runat="server" /></th> 
                    </tr>
                </thead>
                <tbody>    
                    <td><iframe id="player3" type="text/html" runat="server" width="100%" height="300px"
                    src="" frameborder="0"></iframe></td>
                </tbody>
            </table>
        </div> 
    </div>

    <div class ="row">
        <div class ="col-md-4">
            <table class ="table table-striped">   
                <thead>
                    <tr>
                        <th><label type="text" id="streamTitle4" runat="server" /></th> 
                    </tr>
                </thead>
                <tbody>    
                    <td><iframe id="player4" type="text/html" runat="server" width="100%" height="300px"
                    src="" frameborder="0"></iframe></td>
                </tbody>
            </table>
        </div>
        <div class ="col-md-4">
            <table class ="table table-striped">   
                <thead>
                    <tr>
                        <th><label type="text" id="streamTitle5" runat="server" /></th> 
                    </tr>
                </thead>
                <tbody>    
                    <td><iframe id="player5" type="text/html" runat="server" width="100%" height="300px"
                    src="" frameborder="0"></iframe></td>
                </tbody>
            </table>
        </div>
        <div class ="col-md-4">
            <table class ="table table-striped">   
                <thead>
                    <tr>
                        <th><label type="text" id="streamTitle6" runat="server" /></th> 
                    </tr>
                </thead>
                <tbody>    
                    <td><iframe id="player6" type="text/html" runat="server" width="100%" height="300px"
                    src="" frameborder="0"></iframe></td>
                </tbody>
            </table>
        </div>
   </div>

   <div class ="row">  
        <div class ="col-md-4">
            <table class ="table table-striped">   
                <thead>
                    <tr>
                        <th><label type="text" id="streamTitle7" runat="server" /></th> 
                    </tr>
                </thead>
                <tbody>    
                    <td><iframe id="player7" type="text/html" runat="server" width="100%" height="300px"
                    src="" frameborder="0"></iframe></td>
                </tbody>
            </table>
        </div>
        <div class ="col-md-4">
            <table class ="table table-striped">   
                <thead>
                    <tr>
                        <th><label type="text" id="streamTitle8" runat="server" /></th> 
                    </tr>
                </thead>
                <tbody>    
                    <td><iframe id="player8" type="text/html" runat="server" width="100%" height="300px"
                    src="" frameborder="0"></iframe></td>
                </tbody>
            </table>
        </div>
        <div class ="col-md-4">
            <table class ="table table-striped">   
                <thead>
                    <tr>
                        <th><label type="text" id="streamTitle9" runat="server" /></th> 
                    </tr>
                </thead>
                <tbody>    
                    <td><iframe id="player9" type="text/html" runat="server" width="100%" height="300px"
                    src="" frameborder="0"></iframe></td>
                </tbody>
            </table>
        </div>
    </div>
</asp:Content>
