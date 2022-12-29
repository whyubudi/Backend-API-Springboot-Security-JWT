package com.wahyu.portofolio.util;

import com.wahyu.portofolio.dao.RepoSystemParameter;
import com.wahyu.portofolio.dto.DemografiData;
import com.wahyu.portofolio.model.SystemParameter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XMLBuilder {

    public static void createXML(List<DemografiData> dataDemografi, RepoSystemParameter repo) {

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        String fileDate = dateFormat.format(today);
//        String filepath = "/home/itec-admin/Dev Adi/APMS Dashboard/apps/generated_xml/";
        String filepath = "";
        boolean newFile;
        int i = 0;

        SystemParameter params = repo.findAllByParameter("daily_file_sequence");
        int limitData = Integer.parseInt(repo.findAllByParameter("limit_data").getValue());
        int fileSeq = Integer.parseInt(params.getValue());

        String ftpServer = repo.findAllByParameter("ftp_demografi_server").getValue();
        int ftpPort = Integer.parseInt(repo.findAllByParameter("ftp_demografi_port").getValue());
        String ftpUserId = repo.findAllByParameter("ftp_demografi_user").getValue();
        String ftpPassword = repo.findAllByParameter("ftp_demografi_pass").getValue();
        String ftpPath = repo.findAllByParameter("ftp_demografi_path").getValue();

        try {
            while (i < dataDemografi.size()) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();

                Element root = doc.createElement("cards_info");
                doc.appendChild(root);
                root.setAttribute("xmlns","http://bpc.ru/sv/SVXP/card_info");
                Element fileType = doc.createElement("file_type");
                fileType.appendChild(doc.createTextNode("FLTPCINF"));
                root.appendChild(fileType);
                Element instId = doc.createElement("inst_id");
                instId.appendChild(doc.createTextNode("1001"));
                root.appendChild(instId);
                Element tokenPan = doc.createElement("tokenized_pan");
                tokenPan.appendChild(doc.createTextNode("1"));
                root.appendChild(tokenPan);

                int d = 1;
                newFile = false;
                while (!newFile){
                    DemografiData data = dataDemografi.get(i);
                    Element cardInfo = doc.createElement("card_info");
                    root.appendChild(cardInfo);
                    Element cardNum = doc.createElement("card_number");
                    cardNum.appendChild(doc.createTextNode(data.getCardNum()));
                    cardInfo.appendChild(cardNum);
                    Element cardMask = doc.createElement("card_mask");
                    cardMask.appendChild(doc.createTextNode(data.getCardNum().substring(0,6)+"******"+data.getCardNum().substring(12)));
                    cardInfo.appendChild(cardMask);
                    Element cardId = doc.createElement("card_id");
                    cardId.appendChild(doc.createTextNode(data.getCardNum()));
                    cardInfo.appendChild(cardId);
                    Element cardIssDate = doc.createElement("card_iss_date");
                    cardIssDate.appendChild(doc.createTextNode(data.getCardIssDate()));
                    cardInfo.appendChild(cardIssDate);
                    Element cardStartDate = doc.createElement("card_start_date");
                    cardStartDate.appendChild(doc.createTextNode(data.getCardIssDate()));
                    cardInfo.appendChild(cardStartDate);
                    Element expDate = doc.createElement("expiration_date");
                    expDate.appendChild(doc.createTextNode(data.getCardExpDate()));
                    cardInfo.appendChild(expDate);
                    Element instanceId = doc.createElement("instance_id");
                    instanceId.appendChild(doc.createTextNode(String.valueOf(i)));
                    cardInfo.appendChild(instanceId);
                    Element seqNum = doc.createElement("sequential_number");
                    seqNum.appendChild(doc.createTextNode(String.valueOf(d)));
                    cardInfo.appendChild(seqNum);
                    Element cardStatus = doc.createElement("card_status");
                    cardStatus.appendChild(doc.createTextNode(data.getCardStatus()));
                    cardInfo.appendChild(cardStatus);
                    Element cardState = doc.createElement("card_state");
                    cardState.appendChild(doc.createTextNode("CTSE0200"));
                    cardInfo.appendChild(cardState);
                    Element category = doc.createElement("category");
                    category.appendChild(doc.createTextNode(data.getCardCategory()));
                    cardInfo.appendChild(category);
                    Element secWord = doc.createElement("sec_word");
                    cardInfo.appendChild(secWord);
                    Element secQuestion = doc.createElement("secret_question");
                    secQuestion.appendChild(doc.createTextNode("What?"));
                    secWord.appendChild(secQuestion);
                    Element secAnswer = doc.createElement("secret_answer");
                    secAnswer.appendChild(doc.createTextNode("Never"));
                    secWord.appendChild(secAnswer);
                    Element pvv = doc.createElement("pvv");
                    pvv.appendChild(doc.createTextNode("6732"));
                    cardInfo.appendChild(pvv);
                    Element pinUpdtFlag = doc.createElement("pin_update_flag");
                    pinUpdtFlag.appendChild(doc.createTextNode("0"));
                    cardInfo.appendChild(pinUpdtFlag);
                    Element cardTypeId = doc.createElement("card_type_id");
                    cardTypeId.appendChild(doc.createTextNode("1005"));
                    cardInfo.appendChild(cardTypeId);
                    Element prevCardNum = doc.createElement("prev_card_number");
                    prevCardNum.appendChild(doc.createTextNode("3333333333332214"));
                    cardInfo.appendChild(prevCardNum);
                    Element agentNum = doc.createElement("agent_number");
                    agentNum.appendChild(doc.createTextNode(data.getAgentNum()));
                    cardInfo.appendChild(agentNum);
                    Element agentName = doc.createElement("agent_name");
                    agentName.appendChild(doc.createTextNode(data.getAgentName()));
                    cardInfo.appendChild(agentName);
                    Element productNum = doc.createElement("product_number");
                    productNum.appendChild(doc.createTextNode(data.getProductNum()));
                    cardInfo.appendChild(productNum);
                    Element productName = doc.createElement("product_name");
                    productName.appendChild(doc.createTextNode(data.getProductName()));
                    cardInfo.appendChild(productName);
                    Element flexibleData1 = doc.createElement("flexible_data");
                    cardInfo.appendChild(flexibleData1);
                    Element incomeAmount = doc.createElement("field_name");
                    incomeAmount.appendChild(doc.createTextNode("income_amount"));
                    flexibleData1.appendChild(incomeAmount);
                    Element blockAccount = doc.createElement("field_value");
                    blockAccount.appendChild(doc.createTextNode(data.getBlockAccount()));
                    flexibleData1.appendChild(blockAccount);
                    Element flexibleData2 = doc.createElement("flexible_data");
                    cardInfo.appendChild(flexibleData2);
                    Element incomeCurrency = doc.createElement("field_name");
                    incomeCurrency.appendChild(doc.createTextNode("income_currency"));
                    flexibleData2.appendChild(incomeCurrency);
                    Element blockCard = doc.createElement("field_value");
                    blockCard.appendChild(doc.createTextNode(data.getBlockCard()));
                    flexibleData2.appendChild(blockCard);
                    Element customer = doc.createElement("customer");
                    cardInfo.appendChild(customer);
                    Element custNum = doc.createElement("customer_number");
                    custNum.appendChild(doc.createTextNode(data.getCustNum()));
                    customer.appendChild(custNum);
                    Element custRel = doc.createElement("customer_relation");
                    custRel.appendChild(doc.createTextNode("RSCBEXTR"));
                    customer.appendChild(custRel);
                    Element resident = doc.createElement("resident");
                    resident.appendChild(doc.createTextNode("1"));
                    customer.appendChild(resident);
                    Element nationality = doc.createElement("nationality");
                    nationality.appendChild(doc.createTextNode(data.getCustNationality()));
                    customer.appendChild(nationality);
                    for (int n = 0; n < 4; n++) {
                        Element note = doc.createElement("note");
                        customer.appendChild(note);
                        Element noteType = doc.createElement("note_type");
                        noteType.appendChild(doc.createTextNode("NTTP5001"));
                        note.appendChild(noteType);
                        Element noteContent = doc.createElement("note_content");
                        note.appendChild(noteContent);
                        Element language = doc.createElement("language");
                        language.appendChild(doc.createTextNode("LANGENG"));
                        noteContent.appendChild(language);
                        Element noteHeader = doc.createElement("note_header");
                        noteHeader.appendChild(doc.createTextNode("remark"));
                        noteContent.appendChild(noteHeader);
                        Element noteText = doc.createElement("note_text");
                        noteText.appendChild(doc.createTextNode("TEST REMARK"));
                        noteContent.appendChild(noteText);
                        Element startDate = doc.createElement("start_date");
                        startDate.appendChild(doc.createTextNode("2018-04-04"));
                        note.appendChild(startDate);
                        Element endDate = doc.createElement("end_date");
                        endDate.appendChild(doc.createTextNode("2018-11-29"));
                        note.appendChild(endDate);
                    }
                    Element cardholder = doc.createElement("cardholder");
                    cardInfo.appendChild(cardholder);
                    Element crdholdNum = doc.createElement("cardholder_number");
                    crdholdNum.appendChild(doc.createTextNode(data.getCustNum()));
                    cardholder.appendChild(crdholdNum);
                    Element crdholdName = doc.createElement("cardholder_name");
                    crdholdName.appendChild(doc.createTextNode(data.getCustName()));
                    cardholder.appendChild(crdholdName);
                    Element person = doc.createElement("person");
                    cardholder.appendChild(person);
                    Element personName = doc.createElement("person_name");
                    person.appendChild(personName);
                    personName.setAttribute("language","LANGENG");
                    String[] name = data.getCustName().split(" ");
                    Element surname = doc.createElement("surname");
                    surname.appendChild(doc.createTextNode(name[name.length-1]));
                    personName.appendChild(surname);
                    Element firstName = doc.createElement("first_name");
                    firstName.appendChild(doc.createTextNode(name[0]));
                    personName.appendChild(firstName);
                    Element secondName = doc.createElement("second_name");
                    secondName.appendChild(doc.createTextNode(name.length > 1 ? name[1] : ""));
                    personName.appendChild(secondName);
                    Element birthDay = doc.createElement("birthday");
                    birthDay.appendChild(doc.createTextNode(data.getCustBirthDate()));
                    person.appendChild(birthDay);
                    Element birthPlace = doc.createElement("place_of_birth");
                    birthPlace.appendChild(doc.createTextNode("INA"));
                    person.appendChild(birthPlace);
                    Element gender = doc.createElement("gender");
                    gender.appendChild(doc.createTextNode(data.getCustSex()));
                    person.appendChild(gender);
                    Element idCard = doc.createElement("identity_card");
                    person.appendChild(idCard);
                    Element idType = doc.createElement("id_type");
                    idType.appendChild(doc.createTextNode(data.getIdType()));
                    idCard.appendChild(idType);
                    Element idSeries = doc.createElement("id_series");
                    idSeries.appendChild(doc.createTextNode(data.getIdType()));
                    idCard.appendChild(idSeries);
                    Element idNumber = doc.createElement("id_number");
                    idNumber.appendChild(doc.createTextNode(data.getIdNum()));
                    idCard.appendChild(idNumber);
                    Element idIssuer = doc.createElement("id_issuer");
                    idIssuer.appendChild(doc.createTextNode("0"));
                    idCard.appendChild(idIssuer);
                    Element idIssDate = doc.createElement("id_issue_date");
                    idIssDate.appendChild(doc.createTextNode(data.getIdIssDate()));
                    idCard.appendChild(idIssDate);
                    Element idExpDate = doc.createElement("id_expire_date");
                    idExpDate.appendChild(doc.createTextNode(data.getIdExpDate()));
                    idCard.appendChild(idExpDate);
                    Element idDesc = doc.createElement("id_desc");
                    idDesc.appendChild(doc.createTextNode(data.getIdDesc()));
                    idCard.appendChild(idDesc);
                    Element address = doc.createElement("address");
                    cardholder.appendChild(address);
                    address.setAttribute("language","LANGENG");
                    Element addressType = doc.createElement("address_type");
                    addressType.appendChild(doc.createTextNode(data.getAddrType()));
                    address.appendChild(addressType);
                    Element country = doc.createElement("country");
                    country.appendChild(doc.createTextNode("INA"));
                    address.appendChild(country);
                    Element addressName = doc.createElement("address_name");
                    address.appendChild(addressName);
                    Element region = doc.createElement("region");
                    region.appendChild(doc.createTextNode(data.getAddrRegion()));
                    addressName.appendChild(region);
                    Element city = doc.createElement("city");
                    city.appendChild(doc.createTextNode(data.getAddrCity()));
                    addressName.appendChild(city);
                    Element street = doc.createElement("street");
                    street.appendChild(doc.createTextNode(data.getAddrStreet()));
                    addressName.appendChild(street);
                    Element house = doc.createElement("house");
                    house.appendChild(doc.createTextNode("47"));
                    address.appendChild(house);
                    Element apartment = doc.createElement("apartment");
                    apartment.appendChild(doc.createTextNode("5"));
                    address.appendChild(apartment);
                    Element postalCode = doc.createElement("postal_code");
                    postalCode.appendChild(doc.createTextNode(data.getAddrPostCode()));
                    address.appendChild(postalCode);
                    Element placeCode = doc.createElement("place_code");
                    placeCode.appendChild(doc.createTextNode("1258778"));
                    address.appendChild(placeCode);
                    Element regionCode = doc.createElement("region_code");
                    regionCode.appendChild(doc.createTextNode("17797912"));
                    address.appendChild(regionCode);
                    Element contact = doc.createElement("contact");
                    cardholder.appendChild(contact);
                    Element contactType = doc.createElement("contact_type");
                    contactType.appendChild(doc.createTextNode("CNTTPRMC"));
                    contact.appendChild(contactType);
                    String[] commMethodData = {"CMNM0001","CMNM0002"};
                    for (int n = 0; n < 2; n++) {
                        Element contactData = doc.createElement("contact_data");
                        contact.appendChild(contactData);
                        Element communMethod = doc.createElement("commun_method");
                        communMethod.appendChild(doc.createTextNode(commMethodData[n]));
                        contactData.appendChild(communMethod);
                        Element communAddr = doc.createElement("commun_address");
                        communAddr.appendChild(doc.createTextNode(n == 0 ? data.getContactMobile():data.getContactEmail()));
                        contactData.appendChild(communAddr);
                    }
                    Element account = doc.createElement("account");
                    cardInfo.appendChild(account);
                    Element accNum = doc.createElement("account_number");
                    accNum.appendChild(doc.createTextNode(data.getAcctNum()));
                    account.appendChild(accNum);
                    Element currency = doc.createElement("currency");
                    currency.appendChild(doc.createTextNode("360"));
                    account.appendChild(currency);
                    Element accType = doc.createElement("account_type");
                    accType.appendChild(doc.createTextNode("ACTP0100"));
                    account.appendChild(accType);
                    Element accStatus = doc.createElement("account_status");
                    accStatus.appendChild(doc.createTextNode(data.getAcctStatus()));
                    account.appendChild(accStatus);
                    Element precedingInt = doc.createElement("preceding_instance");
                    cardInfo.appendChild(precedingInt);
                    Element limits = doc.createElement("limits");
                    cardInfo.appendChild(limits);
                    String[] limitTypeData = {"LMTP0309","LMTP0305","LMTP0302","LMTP0131"};
                    String[] sumLimitData = {"200000","300000","400000",data.getSumLimit()};
                    for (int n = 0; n < 4; n++) {
                        Element limit = doc.createElement("limit");
                        limits.appendChild(limit);
                        Element limitType = doc.createElement("limit_type");
                        limitType.appendChild(doc.createTextNode(limitTypeData[n]));
                        limit.appendChild(limitType);
                        Element sumLimit = doc.createElement("sum_limit");
                        sumLimit.appendChild(doc.createTextNode(sumLimitData[n]));
                        limit.appendChild(sumLimit);
                        Element countLimit = doc.createElement("count_limit");
                        countLimit.appendChild(doc.createTextNode("789"));
                        limit.appendChild(countLimit);
                        Element sumCurrent = doc.createElement("sum_current");
                        sumCurrent.appendChild(doc.createTextNode("101112"));
                        limit.appendChild(sumCurrent);
                        Element limitCurrency = doc.createElement("currency");
                        limitCurrency.appendChild(doc.createTextNode("360"));
                        limit.appendChild(limitCurrency);
                        Element nextDate = doc.createElement("next_date");
                        nextDate.appendChild(doc.createTextNode("2018-07-06"));
                        limit.appendChild(nextDate);
                        Element lengthType = doc.createElement("length_type");
                        lengthType.appendChild(doc.createTextNode("LEN_TYPE"));
                        limit.appendChild(lengthType);
                        Element cycleLength = doc.createElement("cycle_length");
                        cycleLength.appendChild(doc.createTextNode("100500"));
                        limit.appendChild(cycleLength);
                    }

                    if (d == limitData || i >= dataDemografi.size()-1) {
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
                        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
                        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                        DOMSource domSource = new DOMSource(doc);
                        String filename = "1_cards_"+fileDate+"_"+fileSeq+".xml";
                        StreamResult streamResult = new StreamResult(new File(filepath+filename));
                        transformer.transform(domSource,streamResult);
                        SFTPConnection.sendFileToSFTP(ftpServer,ftpPort,ftpUserId,ftpPassword,ftpPath,filename,filepath);
                        fileSeq++;
                        newFile = true;
                    }
                    i++;
                    d++;
                }
            }
            params.setValue(String.valueOf(fileSeq));
            repo.save(params);

            System.out.println("Build XML file complete!!");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
