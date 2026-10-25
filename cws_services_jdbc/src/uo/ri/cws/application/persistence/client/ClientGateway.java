package uo.ri.cws.application.persistence.client;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientRecord;

public interface ClientGateway extends Gateway<ClientRecord> {

    public class ClientRecord extends Gateway.BaseRecord {
        public String city;
        public String country;
        public String postalCode;
        public String street;
        public String name;
        public String surname;
        public String nif;
        public String phone;
        public String email;
    }
}
