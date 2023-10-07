package com.mustfaibra.qpaymultiplatform.decompose.contactsinfo

import com.mustfaibra.qpaymultiplatform.viewmodels.ContactsViewModel

interface ContactInfoComponent {
	val contactsViewModel: ContactsViewModel
	
	fun onOtpSent()
}
