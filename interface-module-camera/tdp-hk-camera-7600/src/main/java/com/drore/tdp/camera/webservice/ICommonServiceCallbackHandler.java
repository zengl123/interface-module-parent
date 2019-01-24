
/**
 * ICommonServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.4  Built on : Dec 19, 2010 (08:18:42 CET)
 */

    package com.drore.tdp.camera.webservice;

import com.drore.tdp.camera.webservice.ICommonServiceStub;

/**
     *  ICommonServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ICommonServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ICommonServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ICommonServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for receivePictureTag method
            * override this method for handling normal response from receivePictureTag operation
            */
           public void receiveResultreceivePictureTag(
                    ICommonServiceStub.ReceivePictureTagResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from receivePictureTag operation
           */
            public void receiveErrorreceivePictureTag(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServiceByIndexcode method
            * override this method for handling normal response from getServiceByIndexcode operation
            */
           public void receiveResultgetServiceByIndexcode(
                    ICommonServiceStub.GetServiceByIndexcodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServiceByIndexcode operation
           */
            public void receiveErrorgetServiceByIndexcode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteResource method
            * override this method for handling normal response from deleteResource operation
            */
           public void receiveResultdeleteResource(
                    ICommonServiceStub.DeleteResourceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteResource operation
           */
            public void receiveErrordeleteResource(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addResourceWithNotify method
            * override this method for handling normal response from addResourceWithNotify operation
            */
           public void receiveResultaddResourceWithNotify(
                    ICommonServiceStub.AddResourceWithNotifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addResourceWithNotify operation
           */
            public void receiveErroraddResourceWithNotify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pageRoles method
            * override this method for handling normal response from pageRoles operation
            */
           public void receiveResultpageRoles(
                    ICommonServiceStub.PageRolesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pageRoles operation
           */
            public void receiveErrorpageRoles(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServiceByServiceType method
            * override this method for handling normal response from getServiceByServiceType operation
            */
           public void receiveResultgetServiceByServiceType(
                    ICommonServiceStub.GetServiceByServiceTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServiceByServiceType operation
           */
            public void receiveErrorgetServiceByServiceType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getConstant method
            * override this method for handling normal response from getConstant operation
            */
           public void receiveResultgetConstant(
                    ICommonServiceStub.GetConstantResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getConstant operation
           */
            public void receiveErrorgetConstant(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getOnlineUsersByOrgCode method
            * override this method for handling normal response from getOnlineUsersByOrgCode operation
            */
           public void receiveResultgetOnlineUsersByOrgCode(
                    ICommonServiceStub.GetOnlineUsersByOrgCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getOnlineUsersByOrgCode operation
           */
            public void receiveErrorgetOnlineUsersByOrgCode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryUsersByRoleId method
            * override this method for handling normal response from queryUsersByRoleId operation
            */
           public void receiveResultqueryUsersByRoleId(
                    ICommonServiceStub.QueryUsersByRoleIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryUsersByRoleId operation
           */
            public void receiveErrorqueryUsersByRoleId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getPlayUrl method
            * override this method for handling normal response from getPlayUrl operation
            */
           public void receiveResultgetPlayUrl(
                    ICommonServiceStub.GetPlayUrlResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getPlayUrl operation
           */
            public void receiveErrorgetPlayUrl(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllResourceDetailByOrg method
            * override this method for handling normal response from getAllResourceDetailByOrg operation
            */
           public void receiveResultgetAllResourceDetailByOrg(
                    ICommonServiceStub.GetAllResourceDetailByOrgResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllResourceDetailByOrg operation
           */
            public void receiveErrorgetAllResourceDetailByOrg(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllChannelByDeviceAndChannelType method
            * override this method for handling normal response from getAllChannelByDeviceAndChannelType operation
            */
           public void receiveResultgetAllChannelByDeviceAndChannelType(
                    ICommonServiceStub.GetAllChannelByDeviceAndChannelTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllChannelByDeviceAndChannelType operation
           */
            public void receiveErrorgetAllChannelByDeviceAndChannelType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getResourceByXag method
            * override this method for handling normal response from getResourceByXag operation
            */
           public void receiveResultgetResourceByXag(
                    ICommonServiceStub.GetResourceByXagResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getResourceByXag operation
           */
            public void receiveErrorgetResourceByXag(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for findConstantsByKeyPre method
            * override this method for handling normal response from findConstantsByKeyPre operation
            */
           public void receiveResultfindConstantsByKeyPre(
                    ICommonServiceStub.FindConstantsByKeyPreResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from findConstantsByKeyPre operation
           */
            public void receiveErrorfindConstantsByKeyPre(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for xagResourceChangeNotify method
            * override this method for handling normal response from xagResourceChangeNotify operation
            */
           public void receiveResultxagResourceChangeNotify(
                    ICommonServiceStub.XagResourceChangeNotifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from xagResourceChangeNotify operation
           */
            public void receiveErrorxagResourceChangeNotify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServiceByNodeId method
            * override this method for handling normal response from getServiceByNodeId operation
            */
           public void receiveResultgetServiceByNodeId(
                    ICommonServiceStub.GetServiceByNodeIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServiceByNodeId operation
           */
            public void receiveErrorgetServiceByNodeId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getVrmByStoreNode method
            * override this method for handling normal response from getVrmByStoreNode operation
            */
           public void receiveResultgetVrmByStoreNode(
                    ICommonServiceStub.GetVrmByStoreNodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getVrmByStoreNode operation
           */
            public void receiveErrorgetVrmByStoreNode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllServiceNodeByIndexCodes method
            * override this method for handling normal response from getAllServiceNodeByIndexCodes operation
            */
           public void receiveResultgetAllServiceNodeByIndexCodes(
                    ICommonServiceStub.GetAllServiceNodeByIndexCodesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllServiceNodeByIndexCodes operation
           */
            public void receiveErrorgetAllServiceNodeByIndexCodes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for subscribeNotify method
            * override this method for handling normal response from subscribeNotify operation
            */
           public void receiveResultsubscribeNotify(
                    ICommonServiceStub.SubscribeNotifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from subscribeNotify operation
           */
            public void receiveErrorsubscribeNotify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryResourcesByPage method
            * override this method for handling normal response from queryResourcesByPage operation
            */
           public void receiveResultqueryResourcesByPage(
                    ICommonServiceStub.QueryResourcesByPageResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryResourcesByPage operation
           */
            public void receiveErrorqueryResourcesByPage(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listConstant method
            * override this method for handling normal response from listConstant operation
            */
           public void receiveResultlistConstant(
                    ICommonServiceStub.ListConstantResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listConstant operation
           */
            public void receiveErrorlistConstant(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveLogNotify method
            * override this method for handling normal response from saveLogNotify operation
            */
           public void receiveResultsaveLogNotify(
                    ICommonServiceStub.SaveLogNotifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveLogNotify operation
           */
            public void receiveErrorsaveLogNotify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllUsers method
            * override this method for handling normal response from getAllUsers operation
            */
           public void receiveResultgetAllUsers(
                    ICommonServiceStub.GetAllUsersResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllUsers operation
           */
            public void receiveErrorgetAllUsers(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for findResourceByCameraRelation method
            * override this method for handling normal response from findResourceByCameraRelation operation
            */
           public void receiveResultfindResourceByCameraRelation(
                    ICommonServiceStub.FindResourceByCameraRelationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from findResourceByCameraRelation operation
           */
            public void receiveErrorfindResourceByCameraRelation(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryLogNotify method
            * override this method for handling normal response from queryLogNotify operation
            */
           public void receiveResultqueryLogNotify(
                    ICommonServiceStub.QueryLogNotifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryLogNotify operation
           */
            public void receiveErrorqueryLogNotify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteDevice method
            * override this method for handling normal response from deleteDevice operation
            */
           public void receiveResultdeleteDevice(
                    ICommonServiceStub.DeleteDeviceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteDevice operation
           */
            public void receiveErrordeleteDevice(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSubSystemVersion method
            * override this method for handling normal response from getSubSystemVersion operation
            */
           public void receiveResultgetSubSystemVersion(
                    ICommonServiceStub.GetSubSystemVersionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSubSystemVersion operation
           */
            public void receiveErrorgetSubSystemVersion(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for notifyAllResourceSyn method
            * override this method for handling normal response from notifyAllResourceSyn operation
            */
           public void receiveResultnotifyAllResourceSyn(
                    ICommonServiceStub.NotifyAllResourceSynResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from notifyAllResourceSyn operation
           */
            public void receiveErrornotifyAllResourceSyn(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getOnlineUsersByFunction method
            * override this method for handling normal response from getOnlineUsersByFunction operation
            */
           public void receiveResultgetOnlineUsersByFunction(
                    ICommonServiceStub.GetOnlineUsersByFunctionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getOnlineUsersByFunction operation
           */
            public void receiveErrorgetOnlineUsersByFunction(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveSystemLog method
            * override this method for handling normal response from saveSystemLog operation
            */
           public void receiveResultsaveSystemLog(
                    ICommonServiceStub.SaveSystemLogResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveSystemLog operation
           */
            public void receiveErrorsaveSystemLog(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addResource method
            * override this method for handling normal response from addResource operation
            */
           public void receiveResultaddResource(
                    ICommonServiceStub.AddResourceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addResource operation
           */
            public void receiveErroraddResource(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getManagedServices method
            * override this method for handling normal response from getManagedServices operation
            */
           public void receiveResultgetManagedServices(
                    ICommonServiceStub.GetManagedServicesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getManagedServices operation
           */
            public void receiveErrorgetManagedServices(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getResourceDetailByCodes method
            * override this method for handling normal response from getResourceDetailByCodes operation
            */
           public void receiveResultgetResourceDetailByCodes(
                    ICommonServiceStub.GetResourceDetailByCodesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getResourceDetailByCodes operation
           */
            public void receiveErrorgetResourceDetailByCodes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getResourceRelationByIds method
            * override this method for handling normal response from getResourceRelationByIds operation
            */
           public void receiveResultgetResourceRelationByIds(
                    ICommonServiceStub.GetResourceRelationByIdsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getResourceRelationByIds operation
           */
            public void receiveErrorgetResourceRelationByIds(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServiceNodeByNodeType method
            * override this method for handling normal response from getServiceNodeByNodeType operation
            */
           public void receiveResultgetServiceNodeByNodeType(
                    ICommonServiceStub.GetServiceNodeByNodeTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServiceNodeByNodeType operation
           */
            public void receiveErrorgetServiceNodeByNodeType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServiceNodeByType method
            * override this method for handling normal response from getServiceNodeByType operation
            */
           public void receiveResultgetServiceNodeByType(
                    ICommonServiceStub.GetServiceNodeByTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServiceNodeByType operation
           */
            public void receiveErrorgetServiceNodeByType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createIndexCode method
            * override this method for handling normal response from createIndexCode operation
            */
           public void receiveResultcreateIndexCode(
                    ICommonServiceStub.CreateIndexCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createIndexCode operation
           */
            public void receiveErrorcreateIndexCode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServiceOrNodeByIndexcode method
            * override this method for handling normal response from getServiceOrNodeByIndexcode operation
            */
           public void receiveResultgetServiceOrNodeByIndexcode(
                    ICommonServiceStub.GetServiceOrNodeByIndexcodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServiceOrNodeByIndexcode operation
           */
            public void receiveErrorgetServiceOrNodeByIndexcode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getNetDomainId method
            * override this method for handling normal response from getNetDomainId operation
            */
           public void receiveResultgetNetDomainId(
                    ICommonServiceStub.GetNetDomainIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getNetDomainId operation
           */
            public void receiveErrorgetNetDomainId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addSubSytemVersion method
            * override this method for handling normal response from addSubSytemVersion operation
            */
           public void receiveResultaddSubSytemVersion(
                    ICommonServiceStub.AddSubSytemVersionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addSubSytemVersion operation
           */
            public void receiveErroraddSubSytemVersion(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for searchVideoTag method
            * override this method for handling normal response from searchVideoTag operation
            */
           public void receiveResultsearchVideoTag(
                    ICommonServiceStub.SearchVideoTagResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from searchVideoTag operation
           */
            public void receiveErrorsearchVideoTag(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryRolesByUserId method
            * override this method for handling normal response from queryRolesByUserId operation
            */
           public void receiveResultqueryRolesByUserId(
                    ICommonServiceStub.QueryRolesByUserIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryRolesByUserId operation
           */
            public void receiveErrorqueryRolesByUserId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getCascadeInfo method
            * override this method for handling normal response from getCascadeInfo operation
            */
           public void receiveResultgetCascadeInfo(
                    ICommonServiceStub.GetCascadeInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getCascadeInfo operation
           */
            public void receiveErrorgetCascadeInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getDomainIdsByNodeId method
            * override this method for handling normal response from getDomainIdsByNodeId operation
            */
           public void receiveResultgetDomainIdsByNodeId(
                    ICommonServiceStub.GetDomainIdsByNodeIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getDomainIdsByNodeId operation
           */
            public void receiveErrorgetDomainIdsByNodeId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveOperationLog method
            * override this method for handling normal response from saveOperationLog operation
            */
           public void receiveResultsaveOperationLog(
                    ICommonServiceStub.SaveOperationLogResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveOperationLog operation
           */
            public void receiveErrorsaveOperationLog(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for listSystemLog method
            * override this method for handling normal response from listSystemLog operation
            */
           public void receiveResultlistSystemLog(
                    ICommonServiceStub.ListSystemLogResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from listSystemLog operation
           */
            public void receiveErrorlistSystemLog(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addDevice method
            * override this method for handling normal response from addDevice operation
            */
           public void receiveResultaddDevice(
                    ICommonServiceStub.AddDeviceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addDevice operation
           */
            public void receiveErroraddDevice(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getResourceDetailByIds method
            * override this method for handling normal response from getResourceDetailByIds operation
            */
           public void receiveResultgetResourceDetailByIds(
                    ICommonServiceStub.GetResourceDetailByIdsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getResourceDetailByIds operation
           */
            public void receiveErrorgetResourceDetailByIds(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllChannelByDeviceAndDeviceType method
            * override this method for handling normal response from getAllChannelByDeviceAndDeviceType operation
            */
           public void receiveResultgetAllChannelByDeviceAndDeviceType(
                    ICommonServiceStub.GetAllChannelByDeviceAndDeviceTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllChannelByDeviceAndDeviceType operation
           */
            public void receiveErrorgetAllChannelByDeviceAndDeviceType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getFunctionsByApp method
            * override this method for handling normal response from getFunctionsByApp operation
            */
           public void receiveResultgetFunctionsByApp(
                    ICommonServiceStub.GetFunctionsByAppResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getFunctionsByApp operation
           */
            public void receiveErrorgetFunctionsByApp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServiceByIndexcodes method
            * override this method for handling normal response from getServiceByIndexcodes operation
            */
           public void receiveResultgetServiceByIndexcodes(
                    ICommonServiceStub.GetServiceByIndexcodesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServiceByIndexcodes operation
           */
            public void receiveErrorgetServiceByIndexcodes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for updateResource method
            * override this method for handling normal response from updateResource operation
            */
           public void receiveResultupdateResource(
                    ICommonServiceStub.UpdateResourceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from updateResource operation
           */
            public void receiveErrorupdateResource(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServiceOrNodeListByIndexcodes method
            * override this method for handling normal response from getServiceOrNodeListByIndexcodes operation
            */
           public void receiveResultgetServiceOrNodeListByIndexcodes(
                    ICommonServiceStub.GetServiceOrNodeListByIndexcodesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServiceOrNodeListByIndexcodes operation
           */
            public void receiveErrorgetServiceOrNodeListByIndexcodes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getServiceByType method
            * override this method for handling normal response from getServiceByType operation
            */
           public void receiveResultgetServiceByType(
                    ICommonServiceStub.GetServiceByTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getServiceByType operation
           */
            public void receiveErrorgetServiceByType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addResourceType method
            * override this method for handling normal response from addResourceType operation
            */
           public void receiveResultaddResourceType(
                    ICommonServiceStub.AddResourceTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addResourceType operation
           */
            public void receiveErroraddResourceType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getUserDefindTreeListByType method
            * override this method for handling normal response from getUserDefindTreeListByType operation
            */
           public void receiveResultgetUserDefindTreeListByType(
                    ICommonServiceStub.GetUserDefindTreeListByTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getUserDefindTreeListByType operation
           */
            public void receiveErrorgetUserDefindTreeListByType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getXagByResource method
            * override this method for handling normal response from getXagByResource operation
            */
           public void receiveResultgetXagByResource(
                    ICommonServiceStub.GetXagByResourceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getXagByResource operation
           */
            public void receiveErrorgetXagByResource(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAppInfoByCode method
            * override this method for handling normal response from getAppInfoByCode operation
            */
           public void receiveResultgetAppInfoByCode(
                    ICommonServiceStub.GetAppInfoByCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAppInfoByCode operation
           */
            public void receiveErrorgetAppInfoByCode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAllResourceDetail method
            * override this method for handling normal response from getAllResourceDetail operation
            */
           public void receiveResultgetAllResourceDetail(
                    ICommonServiceStub.GetAllResourceDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAllResourceDetail operation
           */
            public void receiveErrorgetAllResourceDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveLogNotifyList method
            * override this method for handling normal response from saveLogNotifyList operation
            */
           public void receiveResultsaveLogNotifyList(
                    ICommonServiceStub.SaveLogNotifyListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveLogNotifyList operation
           */
            public void receiveErrorsaveLogNotifyList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for saveAlarmMessage method
            * override this method for handling normal response from saveAlarmMessage operation
            */
           public void receiveResultsaveAlarmMessage(
                    ICommonServiceStub.SaveAlarmMessageResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from saveAlarmMessage operation
           */
            public void receiveErrorsaveAlarmMessage(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getGobalConfig method
            * override this method for handling normal response from getGobalConfig operation
            */
           public void receiveResultgetGobalConfig(
                    ICommonServiceStub.GetGobalConfigResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getGobalConfig operation
           */
            public void receiveErrorgetGobalConfig(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getDeviceOnlineStatus method
            * override this method for handling normal response from getDeviceOnlineStatus operation
            */
           public void receiveResultgetDeviceOnlineStatus(
                    ICommonServiceStub.GetDeviceOnlineStatusResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getDeviceOnlineStatus operation
           */
            public void receiveErrorgetDeviceOnlineStatus(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteLogNotify method
            * override this method for handling normal response from deleteLogNotify operation
            */
           public void receiveResultdeleteLogNotify(
                    ICommonServiceStub.DeleteLogNotifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteLogNotify operation
           */
            public void receiveErrordeleteLogNotify(java.lang.Exception e) {
            }
                


    }
    