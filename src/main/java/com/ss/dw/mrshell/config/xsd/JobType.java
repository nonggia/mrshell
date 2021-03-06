//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.03.20 at 02:33:09 ���� CST 
//


package com.ss.dw.mrshell.config.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for JobType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JobType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Inputs" type="{}InputsType"/>
 *         &lt;element name="Configs" type="{}ConfigsType" minOccurs="0"/>
 *         &lt;element name="Arguments" type="{}ArgumentsType" minOccurs="0"/>
 *         &lt;element name="Output" type="{}OutputType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" default="HappyJob" />
 *       &lt;attribute name="mapper" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="flow" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="reducer" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="combiner" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="comparator" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="groupingComparator" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="inputFormat" type="{http://www.w3.org/2001/XMLSchema}string" default="org.apache.hadoop.mapreduce.lib.input.TextInputFormat" />
 *       &lt;attribute name="outputFormat" type="{http://www.w3.org/2001/XMLSchema}string" default="org.apache.hadoop.mapreduce.lib.output.TextOutputFormat" />
 *       &lt;attribute name="partitioner" type="{http://www.w3.org/2001/XMLSchema}string" default="org.apache.hadoop.mapreduce.lib.partition.HashPartitioner" />
 *       &lt;attribute name="lzoInput" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="lzoOutput" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *       &lt;attribute name="reducerNum" type="{http://www.w3.org/2001/XMLSchema}int" default="10" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JobType", propOrder = {
    "inputs",
    "configs",
    "arguments",
    "output"
})
public class JobType {

    @XmlElement(name = "Inputs", required = true)
    protected InputsType inputs;
    @XmlElement(name = "Configs")
    protected ConfigsType configs;
    @XmlElement(name = "Arguments")
    protected ArgumentsType arguments;
    @XmlElement(name = "Output", required = true)
    protected OutputType output;
    @XmlAttribute
    protected String name;
    @XmlAttribute(required = true)
    protected String mapper;
    @XmlAttribute(required = true)
    protected String flow;
    @XmlAttribute
    protected String reducer;
    @XmlAttribute
    protected String combiner;
    @XmlAttribute
    protected String comparator;
    @XmlAttribute
    protected String groupingComparator;
    @XmlAttribute
    protected String inputFormat;
    @XmlAttribute
    protected String outputFormat;
    @XmlAttribute
    protected String partitioner;
    @XmlAttribute
    protected Boolean lzoInput;
    @XmlAttribute
    protected Boolean lzoOutput;
    @XmlAttribute
    protected Integer reducerNum;

    /**
     * Gets the value of the inputs property.
     * 
     * @return
     *     possible object is
     *     {@link InputsType }
     *     
     */
    public InputsType getInputs() {
        return inputs;
    }

    /**
     * Sets the value of the inputs property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputsType }
     *     
     */
    public void setInputs(InputsType value) {
        this.inputs = value;
    }

    /**
     * Gets the value of the configs property.
     * 
     * @return
     *     possible object is
     *     {@link ConfigsType }
     *     
     */
    public ConfigsType getConfigs() {
        return configs;
    }

    /**
     * Sets the value of the configs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigsType }
     *     
     */
    public void setConfigs(ConfigsType value) {
        this.configs = value;
    }

    /**
     * Gets the value of the arguments property.
     * 
     * @return
     *     possible object is
     *     {@link ArgumentsType }
     *     
     */
    public ArgumentsType getArguments() {
        return arguments;
    }

    /**
     * Sets the value of the arguments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArgumentsType }
     *     
     */
    public void setArguments(ArgumentsType value) {
        this.arguments = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * @return
     *     possible object is
     *     {@link OutputType }
     *     
     */
    public OutputType getOutput() {
        return output;
    }

    /**
     * Sets the value of the output property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputType }
     *     
     */
    public void setOutput(OutputType value) {
        this.output = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        if (name == null) {
            return "HappyJob";
        } else {
            return name;
        }
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the mapper property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapper() {
        return mapper;
    }

    /**
     * Sets the value of the mapper property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapper(String value) {
        this.mapper = value;
    }

    /**
     * Gets the value of the flow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlow() {
        return flow;
    }

    /**
     * Sets the value of the flow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlow(String value) {
        this.flow = value;
    }

    /**
     * Gets the value of the reducer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReducer() {
        return reducer;
    }

    /**
     * Sets the value of the reducer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReducer(String value) {
        this.reducer = value;
    }

    /**
     * Gets the value of the combiner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCombiner() {
        return combiner;
    }

    /**
     * Sets the value of the combiner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCombiner(String value) {
        this.combiner = value;
    }

    /**
     * Gets the value of the comparator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComparator() {
        return comparator;
    }

    /**
     * Sets the value of the comparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComparator(String value) {
        this.comparator = value;
    }

    /**
     * Gets the value of the groupingComparator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupingComparator() {
        return groupingComparator;
    }

    /**
     * Sets the value of the groupingComparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupingComparator(String value) {
        this.groupingComparator = value;
    }

    /**
     * Gets the value of the inputFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInputFormat() {
        if (inputFormat == null) {
            return "org.apache.hadoop.mapreduce.lib.input.TextInputFormat";
        } else {
            return inputFormat;
        }
    }

    /**
     * Sets the value of the inputFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInputFormat(String value) {
        this.inputFormat = value;
    }

    /**
     * Gets the value of the outputFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutputFormat() {
        if (outputFormat == null) {
            return "org.apache.hadoop.mapreduce.lib.output.TextOutputFormat";
        } else {
            return outputFormat;
        }
    }

    /**
     * Sets the value of the outputFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutputFormat(String value) {
        this.outputFormat = value;
    }

    /**
     * Gets the value of the partitioner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartitioner() {
        if (partitioner == null) {
            return "org.apache.hadoop.mapreduce.lib.partition.HashPartitioner";
        } else {
            return partitioner;
        }
    }

    /**
     * Sets the value of the partitioner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartitioner(String value) {
        this.partitioner = value;
    }

    /**
     * Gets the value of the lzoInput property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isLzoInput() {
        if (lzoInput == null) {
            return false;
        } else {
            return lzoInput;
        }
    }

    /**
     * Sets the value of the lzoInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLzoInput(Boolean value) {
        this.lzoInput = value;
    }

    /**
     * Gets the value of the lzoOutput property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isLzoOutput() {
        if (lzoOutput == null) {
            return false;
        } else {
            return lzoOutput;
        }
    }

    /**
     * Sets the value of the lzoOutput property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLzoOutput(Boolean value) {
        this.lzoOutput = value;
    }

    /**
     * Gets the value of the reducerNum property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getReducerNum() {
        if (reducerNum == null) {
            return  10;
        } else {
            return reducerNum;
        }
    }

    /**
     * Sets the value of the reducerNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReducerNum(Integer value) {
        this.reducerNum = value;
    }

}
