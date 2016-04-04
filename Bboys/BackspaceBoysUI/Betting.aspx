<%@ Page Title="Betting Page" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Betting.aspx.cs" Inherits="BackspaceBoysUI.Betting" %>
<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="server">
    
    <div class="jumbotron">
        <h1 style="text-align: center"><strong>Betting Page</strong></h1>
        <h3 style="text-align: center">Select a team and start betting away!</h3>
 
    </div>
    <h2 style="text-align: center" id="streamTitle" runat="server"></h2> 
    <center><a id="gameInfoLink" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="gameInfoLabel" runat="server" /></a></center>
    <div class="row">
        
        <div class="col-md-9">
                <center><iframe id="betPlayer" type="text/html" runat="server" width="100%" height="400px"
                src="http://www.twitch.tv/strifecro/embed" frameborder="0"></iframe></center>
        </div>

        <div class="col-md-3">
            <right>
            <iframe frameborder="0" scrolling="no" id="chat_embed" 
            src="http://www.twitch.tv/summit1g/chat" height="400px" width="300px"> </iframe></right>
        </div>
    </div>
    <br /> <br >
    <div class="row">

        <div class="col-md-5">
            <br />
            <table class="nav-justified" style="text-align: center">
            <tr>
                <td>
                    <h3><strong><u>Team:</u></strong></h3>
                    <asp:RadioButton ID="team1button" runat="server" Text="Team 1" GroupName="BetTeams" />
                    <asp:RadioButton ID="team2button" runat="server" Text="Team 2" GroupName="BetTeams"/>
                </td>
            </tr>
            <tr>
                <td>
                    <p>Amount (whole numbers only, E.g. 75):</p>
                    <asp:TextBox ID="amountbox" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>
                    <br />
                    <asp:Button ID="Button1" Font-Bold="True" runat="server" OnClick="Button1_Click" Text="Submit" />
                    <br /> <br />
                    <asp:Label ID="Label3" runat="server" Text=""></asp:Label> 
                    <br /> <br />
                    <asp:Label ID="Label2" runat="server" Text="Label" Visible="False"></asp:Label>
                </td>
            </tr>
            </table>
        </div>

        <div class="col-md-7">
            <br />
            <center><h3><strong><u>Bets Made:</u></strong></h3></center>
            <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:NewBetConnectionString %>" SelectCommand="SELECT [User], [TeamChosen], [MatchName], [BetAmount] FROM [OpenBets]"></asp:SqlDataSource>
            <center><asp:GridView ID="GridView1" runat="server" AllowPaging="True" AutoGenerateColumns="False" DataSourceID="SqlDataSource1" BackColor="White" BorderColor="#CCCCCC" BorderStyle="None" BorderWidth="1px" CellPadding="3">
            <Columns>
                <asp:BoundField DataField="User" HeaderText="User" SortExpression="User" />
                <asp:BoundField DataField="TeamChosen" HeaderText="TeamChosen" SortExpression="TeamChosen" />
                <asp:BoundField DataField="MatchName" HeaderText="MatchName" SortExpression="MatchName" />
                <asp:BoundField DataField="BetAmount" HeaderText="BetAmount" SortExpression="BetAmount" />
            </Columns>
            <FooterStyle BackColor="White" ForeColor="#000066" />
            <HeaderStyle BackColor="#2E2E2E" Font-Bold="True" ForeColor="White" />
            <PagerStyle BackColor="White" ForeColor="#000066" HorizontalAlign="Left" />
            <RowStyle ForeColor="#000066" />
            <SelectedRowStyle BackColor="#669999" Font-Bold="True" ForeColor="White" />
            <SortedAscendingCellStyle BackColor="#F1F1F1" />
            <SortedAscendingHeaderStyle BackColor="#007DBB" />
            <SortedDescendingCellStyle BackColor="#CAC9C9" />
            <SortedDescendingHeaderStyle BackColor="#00547E" />
            </asp:GridView> </center>
 
        </div>
    </div>
   
</asp:Content>
