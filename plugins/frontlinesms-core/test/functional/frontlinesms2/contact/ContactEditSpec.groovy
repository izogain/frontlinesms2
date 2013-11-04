package frontlinesms2.contact

import frontlinesms2.*

import geb.Browser
import grails.plugin.geb.GebSpec
import frontlinesms2.message.*

class ContactEditSpec extends ContactBaseSpec {
	def setup() {
		createTestContacts()
	}
	
	def 'selected contact details can be edited and saved'() {
		given:
			def aliceId = remote { Contact.findByName('Alice').id }
		when:
			to PageContactShow, aliceId

			singleContactDetails.name.value('Kate')
			singleContactDetails.name.blur()

			singleContactDetails.mobile.value('+2541234567')
			singleContactDetails.mobile.blur()

			singleContactDetails.email.value('gaga@gmail.com')
			singleContactDetails.email.blur()
		then:
			assertFieldDetailsCorrect('name', 'Name', 'Kate')
			assertFieldDetailsCorrect('mobile', 'Mobile', '+2541234567')
			remote { Contact.findById(aliceId).name } == 'Kate'
	}
	
	def "should disable the save and cancel buttons when viewing a contact details"() {
		when:
			to PageContactShow, remote { Contact.findByName('Bob').id }
		then:
			singleContactDetails.save.disabled
	}
	
	def "should remain on the same page after updating a contact"() {
		given:
			createManyContacts()
		when:
			to PageContactShow, remote { Contact.findByName('Bob').id }
			footer.nextPage.click()
		then:
			!footer.prevPage.disabled
		when:
			singleContactDetails.name = 'Kate'
			singleContactDetails.mobile.click()
		then:
			!footer.prevPage.disabled
	}

	def "should display a count of messages recieved and sent for a contact"() {
		given: 'A contact has received and sent messages'
			remote {
				def sent1 = new Fmessage(inbound:false, text:"outbound 1")
				def sent2 = new Fmessage(inbound:false, text:"outbound 2")
				sent1.addToDispatches(dst:'2541234567', status:DispatchStatus.SENT, dateSent:new Date()).save(failOnError:true, flush:true)
				sent2.addToDispatches(dst:'2541234567', status:DispatchStatus.SENT, dateSent:new Date()).save(failOnError:true, flush:true)
				new Fmessage(src:'2541234567', text:"inbound 1", date: new Date(), inbound:true).save(failOnError:true, flush:true)
				null
			}
		when:
			to PageContactShow, remote { Contact.findByName('Alice').id }
		then:
			singleContactDetails.sentCount == 'contact.messages.sent[2]'
			singleContactDetails.receivedCount == 'contact.received.messages[1]'
	}
}

