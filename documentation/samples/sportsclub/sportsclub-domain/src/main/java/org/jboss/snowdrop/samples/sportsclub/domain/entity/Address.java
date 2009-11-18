package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import javax.persistence.Embeddable;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@Embeddable
public class Address
{

   private String streetAddress;

   private String city;

   private String provinceOrState;

   private String country;

   private String postalCode;

   public String getCity()
   {
      return city;
   }


   public void setCity(String city)
   {
      this.city = city;
   }

   public String getProvinceOrState()
   {
      return provinceOrState;
   }

   public void setProvinceOrState(String provinceOrState)
   {
      this.provinceOrState = provinceOrState;
   }

   public String getStreetAddress()
   {
      return streetAddress;
   }

   public void setStreetAddress(String streetAddress)
   {
      this.streetAddress = streetAddress;
   }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }
}
