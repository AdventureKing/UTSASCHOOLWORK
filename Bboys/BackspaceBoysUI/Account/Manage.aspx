
<%@ Page Title="Manage Account" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeBehind="Manage.aspx.cs" Inherits="BackspaceBoysUI.Account.Manage" %>

<%@ Register Src="~/Account/OpenAuthProviders.ascx" TagPrefix="uc" TagName="OpenAuthProviders" %>

<asp:Content ContentPlaceHolderID="MainContent" runat="server">
    <div class ="jumbotron">
        <h1 style="text-align: center"><strong><%: Title %></strong></h1>
        <h4 style="text-align: center">Change your account settings</h4>
     </div>

    <div>
        <asp:PlaceHolder runat="server" ID="successMessage" Visible="false" ViewStateMode="Disabled">
            <p class="text-success"><%: SuccessMessage %></p>
        </asp:PlaceHolder>
    </div>


    <div class="row">
        <div class="col-md-4">
            <div class="form-horizontal">
                <hr />
                <dl class="dl-horizontal">
                    <dt>Profile Image:</dt>
                    <dd>
                        <img src="../Images/profileimg.png" height="100" width="100"/>
                       
                    </dd>
                    <br />
                    <dt>Username:</dt>
                    <dd>
                         <asp:Label ID="Label1" runat="server" Text="Label"></asp:Label>
                    </dd>
                    <br />
                    <dt>Password:</dt>
                    <dd>
                        <asp:HyperLink NavigateUrl="/Account/ManagePassword" Text="[Change]" Visible="false" ID="ChangePassword" runat="server" />
                        <asp:HyperLink NavigateUrl="/Account/ManagePassword" Text="[Create]" Visible="false" ID="CreatePassword" runat="server" />
                    </dd>
                    <br />
                    <dt>Space Bucks</dt>
                    <dd>
                        &nbsp;<asp:Label ID="Label2" runat="server" Text="Label"></asp:Label> SB
                    </dd>
                </dl>
            </div>
        </div>
        <div class="col-md-8">
            <h3 style="text-align: center"><strong><u>Your Recent Bets:</u></strong></h3>
            <br />                 
            <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:NewBetConnectionString %>" SelectCommand="SELECT DISTINCT [User], [TeamChosen] AS TeamChosen, [MatchName] AS MatchName, [BetAmount] AS BetAmount FROM [OpenBets] WHERE ([User] = @User)">
            <SelectParameters>
                <asp:ControlParameter ControlID="Label1" Name="User" PropertyName="Text" Type="String" />
            </SelectParameters>
            </asp:SqlDataSource>
            <center><asp:GridView ID="GridView1" runat="server" AllowPaging="True" AutoGenerateColumns="False" DataSourceID="SqlDataSource1">
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
            </asp:GridView></center>
        </div>
    </div>

</asp:Content>