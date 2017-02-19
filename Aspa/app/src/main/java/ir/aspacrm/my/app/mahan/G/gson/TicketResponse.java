package ir.aspacrm.my.app.mahan.G.gson;

import ir.aspacrm.my.app.mahan.G.G.enums.EnumTicketPriority;

/**
 * Created by Microsoft on 3/15/2016.
 */
public class TicketResponse {
    public long Code;
    public String Date = "";
    public String Title = "" ;
    public String State = "";
    public String Priority = EnumTicketPriority.KAM;
    public int Open = 0;
}
