package br.com.soluevo.cobrei.application.commom.utils.facebookmanager

import br.com.soluevo.facebookmanagerlibrary.UserFacebook

interface FacebookCallBack {

    fun fbCallbackOnSuccess(userFacebook: UserFacebook)
    fun fbCallbackOnError(localizedMessage: String)
    fun fbCallbackOnCancel()
}
