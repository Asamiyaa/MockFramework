//package com.pattern.g;
//
//
//import javax.xml.bind.annotation.XmlAttribute;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlType;
//
//@XmlType( propOrder = { "name", "capital", "foundation", "continent" , "population"} )
//@XmlRootElement( name = "Country" )
//    public class Country
//    {
//
//        private String name ;
//
//        @XmlElement(name = "Country_Population")
//        public void setPopulation( int population )
//        {
//            this.population = population;
//        }
//
//
//        @XmlElement( name = "Country_Name" )
//        public void setName( String name )
//        {
//            this.name = name;
//        }
//
//        @XmlElement( name = "Country_Capital" )
//        public void setCapital( String capital )
//        {
//            this.capital = capital;
//        }
//        @XmlAttribute( name = "importance", required = true )
//        public void setImportance( int importance )
//        {
//            this.importance = importance;
//        }
//}
