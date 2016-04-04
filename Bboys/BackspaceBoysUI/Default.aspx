<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="BackspaceBoysUI._Default" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">

    <div class="jumbotron">
        <h1 style="text-align: center"><strong>BackSpace Bets</strong></h1>
        <h2 class="lead" style="text-align: center">Welcome to the homepage!</h2>
    </div>

    <div class="row">
        <div class="col-md-3">
            <h2 style="text-align: center"><strong>News Feed</strong></h2>
            <hr />
            <ul class="list-group">
                <li class="list-group-item"><a id="newslistLink0" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="newsList0" runat="server" /></a></li>
                <li class="list-group-item"><a id="newslistLink1" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="newsList1" runat="server" /></a></li>
                <li class="list-group-item"><a id="newslistLink2" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="newsList2" runat="server" /></a></li>
                <li class="list-group-item"><a id="newslistLink3" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="newsList3" runat="server" /></a></li>
                <li class="list-group-item"><a id="newslistLink4" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="newsList4" runat="server" /></a></li>
                <li class="list-group-item"><a id="newslistLink5" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="newsList5" runat="server" /></a></li>
                <li class="list-group-item"><a id="newslistLink6" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="newsList6" runat="server" /></a></li>
                <li class="list-group-item"><a id="newslistLink7" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="newsList7" runat="server" /></a></li>
                <li class="list-group-item"><a id="newslistLink8" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="newsList8" runat="server" /></a></li>
                <li class="list-group-item"><a id="newslistLink9" href="http://www.w3schools.com/html/" runat="server"><label type="text" id="newsList9" runat="server" /></a></li>
            </ul>
        </div>

        <div class="col-md-5">
            
            <h2 style="text-align: center"><strong>Recent Bets</strong></h2>
            <hr />
            <asp:GridView ID="GridView1" runat="server" AutoGenerateColumns="False" DataSourceID="SqlDataSource1" BackColor="White" BorderColor="#CCCCCC" BorderStyle="None" BorderWidth="1px" CellPadding="3" HorizontalAlign="Right">
            <Columns>
                <asp:BoundField DataField="User" HeaderText="User" SortExpression="User" />
                <asp:BoundField DataField="TeamChosen" HeaderText="TeamChosen" SortExpression="TeamChosen" />
                <asp:BoundField DataField="MatchName" HeaderText="MatchName" SortExpression="MatchName" />
                <asp:BoundField DataField="BetAmount" HeaderText="BetAmount" SortExpression="BetAmount" />
            </Columns>
            <FooterStyle BackColor="White" ForeColor="#000066" />
            <HeaderStyle BackColor="#2E2E2E" Font-Bold="True" ForeColor="White" />
            <PagerStyle BackColor="White" ForeColor="#000066" HorizontalAlign="Center" />
            <RowStyle ForeColor="#000066" />
            <SelectedRowStyle BackColor="#669999" Font-Bold="True" ForeColor="White" />
            <SortedAscendingCellStyle BackColor="#F1F1F1" />
            <SortedAscendingHeaderStyle BackColor="#007DBB" />
            <SortedDescendingCellStyle BackColor="#CAC9C9" />
            <SortedDescendingHeaderStyle BackColor="#00547E" />
            </asp:GridView>
            <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:NewBetConnectionString %>" SelectCommand="SELECT [User], [TeamChosen], [MatchName], [BetAmount] FROM [OpenBets] ORDER BY [Id] DESC"></asp:SqlDataSource>
        </div>

        <div class="col-md-4">
            <h2 style="text-align: center"><strong>Try a Bet</strong></h2>
            <hr />
                <p style="text-align: center"><strong>
                    Select one of the bets below to start your bidding wars:</strong>
                </p>
            <div class="container" runat="server" id="container">
                <p style="text-align: center" id="container1" runat="server">
                </p>
            </div>
        </div>
        
    </div>

 </asp:Content>
