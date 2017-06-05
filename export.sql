--------------------------------------------------------
--  File created - Monday-July-13-2015   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Package Body APPSERVICES
--------------------------------------------------------

  CREATE OR REPLACE PACKAGE BODY "NATMOBILE"."APPSERVICES" AS
 procedure balinquiry(
                      custcode IN varchar2,
                    
                             acctype IN varchar2,                                  
                      vchilds OUT VARCHAR2,
                      vpayments OUT SYS_REFCURSOR
                      
                      
                    
                      )as
                       csID NUMBER;
                        csAcc  NUMBER;
                        cust VARCHAR2(100);
 
  BEGIN
--  if (acctype ='2') then
--   SELECT Count(*) into csID from IMAL_ACCOUNT_INFO_VW where CUSTOMERCODE = custcode;
--   else if(acctype = '1') then
--   
--   end if;

SELECT Count(*) into csID 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 

   if(csID = 0) then
    vchilds := 'No accounts registered to this customer';
    ELSE

SELECT m.CUSTOMER_CODE into cust 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 
    vchilds := 'SUCCESS';
 if (acctype ='imal') then
  open vpayments for
 SELECT accountid, bookedbalance, clearedbalance,dormantstatus,fullname,currency,productid from 
 IMAL_ACCOUNT_INFO_VW where CUSTOMERCODE = cust;
 
 else if(acctype = 'bfub') then
  open vpayments for
   SELECT accountid, bookedbalance, clearedbalance,dormantstatus,fullname,currency,PRODUCTID from 
 BFUB_ACCOUNT_INFO_VW where CUSTOMERCODE = cust;
   end if;
 end if;

     end if;

  END balinquiry;
  
   procedure getcards(
                      accno IN varchar2,
                    
                                                     
                      vchilds OUT VARCHAR2,
                      vpayments OUT SYS_REFCURSOR
                      
                      
                    
                      )as
                       csID NUMBER;
                        csAcc  NUMBER;
                        cust VARCHAR2(100);
 
  BEGIN
--  if (acctype ='2') then
--   SELECT Count(*) into csID from IMAL_ACCOUNT_INFO_VW where CUSTOMERCODE = custcode;
--   else if(acctype = '1') then
--   
--   end if;

SELECT count(*) INTO csID FROM CARD_INFO_VW WHERE BFUBACCOUNT = accno;

   if(csID = 0) then
    vchilds := 'No cards registered to this customer';
    ELSE


    vchilds := 'SUCCESS';
 
  open vpayments for
SELECT PAN,CARDTYPE,ACCOUNTNO,BFUBACCOUNT,INSTITUTION,CARDLIMIT,BALANCE,CARDSTATUS FROM CARD_INFO_VW WHERE BFUBACCOUNT = accno;
 
 
 end if;

    

  END getcards;
  
   procedure getloans(
                      custcode IN varchar2,
                    
                                                     
                      vchilds OUT VARCHAR2,
                      vpayments OUT SYS_REFCURSOR
                      
                      
                    
                      )as
                       csID NUMBER;
                        csAcc  NUMBER;
                        cust VARCHAR2(100);
 
  BEGIN
SELECT Count(*) into csID 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 

   if(csID = 0) then
    vchilds := 'No accounts registered to this customer';
    ELSE

SELECT m.CUSTOMER_CODE into cust 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 
    vchilds := 'SUCCESS';
 
  open vpayments for

SELECT  LOANACCOUNTNUMBER,PRINCIPALLOAN,TERM,LOANBALANCE,INTERESTRATE,INSTALLMENTAMOUNT,LOANSTATUS,LOANDESCRIPTION   FROM LOANBALANCE_VW where CUSTOMERID = cust; 
 
 
 end if;

    

  END getloans;
     procedure genuserid(
                      custcode IN varchar2,
                      userid IN varchar2,
                    
                                                     
                      vchilds OUT VARCHAR2
                      
                      
                    
                      )as
                       csID NUMBER;
                        csAcc  NUMBER;
                        cust VARCHAR2(100);
                        hasus VARCHAR2(100);
 
  BEGIN
SELECT Count(*) into csID 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 

   if(csID = 0) then
    vchilds := 'No accounts registered to this customer';
    ELSE

SELECT m.CUSTOMER_CODE into cust
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 
SELECT m.USER_ID into hasus
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 
    vchilds := 'SUCCESS';
 


 update MOB_CUSTOMER_MASTER set USER_ID=userid where CUSTOMER_CODE = cust;

 
       
 
 end if;

    

  END genuserid;
  
   procedure delben(
                      benid IN varchar2,
                    
                    
                                                     
                      vchilds OUT VARCHAR2
                      
                      
                    
                      )as
                       csID NUMBER;
                       
 
  BEGIN
SELECT Count(*) into csID 
FROM BENEFICIARY_DETAILS
 where COMMONID = benid; 

   if(csID = 0) then
    vchilds := 'No beneficiary with the supplied details exists in the system';
    ELSE


    vchilds := 'SUCCESS';
 


 update BENEFICIARY_DETAILS set BEN_FLAG = 'N' where COMMONID = benid;

 
       
 
 end if;

    

  END delben;
   procedure getinbox(
                      mobno IN varchar2,
                    
                                                     
                      vchilds OUT VARCHAR2,
                      vpayments OUT SYS_REFCURSOR
                      
                      
                    
                      )as
                       csID NUMBER;
                        csAcc  NUMBER;
                        cust VARCHAR2(100);
 
  BEGIN
SELECT COUNT(*) into csID FROM (SELECT MSG_DATE,MESSAGE FROM ALERTS WHERE MOBILE_NO = '254718754578') WHERE ROWNUM <10;

   if(csID = 0) then
    vchilds := 'You do not have any inbox messages';
    ELSE


    vchilds := 'SUCCESS';
 
  open vpayments for
SELECT * FROM (SELECT MSG_DATE,MESSAGE FROM ALERTS WHERE MOBILE_NO = mobno) WHERE ROWNUM <10;
 
 
 end if;

    

  END getinbox;
  
  procedure getprofile(
                      custcode IN varchar2,
                    
                                                             
                      vchilds OUT VARCHAR2,
                      vpayments OUT SYS_REFCURSOR,
                      vmobno OUT SYS_REFCURSOR
                      
                      
                    
                      )as
                       csID NUMBER;
                        csAcc  NUMBER;
                        cust VARCHAR2(100);
 
  BEGIN
--  if (acctype ='2') then
--   SELECT Count(*) into csID from IMAL_ACCOUNT_INFO_VW where CUSTOMERCODE = custcode;
--   else if(acctype = '1') then
--   
--   end if;

SELECT Count(*) into csID 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 

   if(csID = 0) then
    vchilds := 'No accounts registered to this customer';
    else   if(csID > 1) then
    vchilds := 'The mobile number is mapped to different customers. Please check this';
    ELSE

SELECT m.CUSTOMER_CODE into cust 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 
    vchilds := 'SUCCESS';

  open vpayments for
SELECT FIRST_NAME,LAST_NAME,DOCID,EMAILID,GENDER,USER_ID from 
 MOB_CUSTOMER_MASTER where CUSTOMER_CODE = cust;
 
 open vmobno for
 SELECT cs.MOBILE_NUMBER
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where m.CUSTOMER_CODE = cust; 
 end if;
 end if;


   

  END getprofile;
   procedure addben(
                      custcode IN varchar2,
                    
                             benname IN varchar2,   
                               benacno IN varchar2,
                                 benmobno IN varchar2,
                                  bentype IN varchar2,
                                 benbank IN varchar2,
                                  benbranch IN varchar2,
                               
                      vchilds OUT VARCHAR2
                     
                      
                      
                    
                      )as
                       csID NUMBER;
                       cust VARCHAR2(100);
 
  BEGIN
  SELECT Count(*) into csID 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 

   if(csID = 0) then
    vchilds := 'No accounts registered to this customer';
    ELSE

SELECT m.CUSTOMER_CODE into cust 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 
    vchilds := 'SUCCESS';
  
       insert into BENEFICIARY_DETAILS (COMMONID,SHORT_NAME,BEN_ACCID, MSISDN, BEN_FLAG,  CUST_ID,BEN_TYPE,BANK,BRANCH) 
             values(ADD_BENEFICIARY_SEQ.nextval, benname, benacno, benmobno,'Y',  cust,bentype,benbank,benbranch);
            end if;
            end addben;
            
procedure logFullStatementRequest(
                      accno IN varchar2,
                             periodfrom IN varchar2,   
                               periodto IN varchar2,
                                 certified IN varchar2,
                                 email IN varchar2,
                                 branch IN varchar2,
                                 msisdn IN varchar2,
                      vchilds OUT VARCHAR2
                     
                      
                      
                    
                      )as
                      
  BEGIN
 
    vchilds := 'SUCCESS';
    
       insert into MOB_FULL_STATEMENT_REQUEST (ID,ACCOUNT_NUMBER,PERIODFROM, PERIODTO, CERTIFIEDSTATEMENT,  DELIVER_EMAIL,DELIVER_BRANCH,MSISDN) 
             values(MOB_FULL_STATEMENT_REQ_SEQ.nextval, accno,periodfrom,periodto,certified,email,branch,msisdn);
            end logFullStatementRequest;
            
            procedure logInviteFriend(
                      guestName IN varchar2,
                             guestMsisdn IN varchar2,   
                               msisdn IN varchar2,
                               dateInvited IN TIMESTAMP,
                               vchilds OUT VARCHAR2
                                          )as
                      csID number;
  BEGIN
 SELECT Count(*) into csID from MOB_INVITE_FRIEND where GUEST_MSISDN = guestMsisdn;
 if(csID = 0) then
    vchilds := 'SUCCESS';
    
       insert into MOB_INVITE_FRIEND (ID,MSISDN,GUEST_MSISDN,GUEST_NAME,DATE_INVITED) 
             values(MOB_INVITE_SEQ.nextval,msisdn,guestMsisdn, guestName,dateInvited);
             else
              vchilds := 'The selected friend number has already been sent an invite';
              end if;
            end logInviteFriend;
 procedure loadben(
                      custcode IN varchar2,
                     
                          
                      vchilds OUT VARCHAR2,
                     
                        vpayments OUT SYS_REFCURSOR
                      
                    
                      )as
                       csID NUMBER;
                       cust VARCHAR2(100);
 
  BEGIN
  SELECT Count(*) into csID 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 

   if(csID = 0) then
    vchilds := 'No accounts registered to this customer';
    ELSE

SELECT m.CUSTOMER_CODE into cust 
FROM MOB_CUSTOMER_MASTER m
INNER JOIN MOB_CONTACT_INFO cs ON m.ID = cs.CUST_ID where cs.MOBILE_NUMBER = custcode; 
    vchilds := 'SUCCESS';
  
  open vpayments for
SELECT BEN_ACCID,MSISDN,SHORT_NAME,COMMONID FROM BENEFICIARY_DETAILS WHERE BEN_FLAG = 'Y' and CUST_ID = cust;
 
            end if;
            end loadben;
             procedure loadproducts(
                      vchilds OUT VARCHAR2,
                     
                        vpayments OUT SYS_REFCURSOR
                      
                    
                      )as
                       csID NUMBER;
                       cust VARCHAR2(100);
 
  BEGIN
 SELECT Count(*) into csID FROM MOB_PRODUCTS;
 
   if(csID = 0) then
    vchilds := 'No products available';
    ELSE

    vchilds := 'SUCCESS';
  
  open vpayments for
SELECT DESCRIPTION,PRODUCTID FROM MOB_PRODUCTS;
 
            end if;
            end loadproducts;
END APPSERVICES;

/
