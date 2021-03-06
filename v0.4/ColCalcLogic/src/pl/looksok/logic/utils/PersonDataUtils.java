package pl.looksok.logic.utils;

import java.util.ArrayList;
import java.util.List;

import pl.looksok.logic.AtomPayment;
import pl.looksok.logic.PersonData;

public class PersonDataUtils {
	public static double returnMoneyToPersonB(String personBName, double howMuchPersonBPaid,
			double howMuchPersonBShouldPay, double howMuchRefundPersonBNeeds,
			PersonData pd) {
		double tmpToReturn = pd.getHowMuchPersonShouldPay() - pd.getHowMuchIPaid();
		
		if(personBNeedsMoreThanIShouldGive(tmpToReturn, pd.getAlreadyReturned(), pd.getToReturn())){
			tmpToReturn = splitMyReturnAmount(pd);
		}else if(personBWantsLessThanIHaveToReturn(pd, howMuchPersonBPaid, howMuchPersonBShouldPay, tmpToReturn, howMuchRefundPersonBNeeds)){
			tmpToReturn = givePersonBNoMoreThanHeWants(pd, howMuchPersonBPaid, howMuchPersonBShouldPay);
		}

		if(tmpToReturn > howMuchRefundPersonBNeeds){
			tmpToReturn = howMuchRefundPersonBNeeds;
		}

		if(tmpToReturn<0.0)
			tmpToReturn = 0.0;

		pd.setAlreadyReturned(pd.getAlreadyReturned() + tmpToReturn);
		PersonData personBData = pd.getOtherPeoplePayments().get(personBName);
		personBData.setAlreadyRefunded(personBData.getAlreadyRefunded() + tmpToReturn);
		return tmpToReturn;
	}
	
	private static double givePersonBNoMoreThanHeWants(PersonData pd, double howMuchPersonBPaid, double howMuchPersonBShouldPay) {
		double tmpToReturn;
		double refundForPersonBNeeded = howMuchPersonBPaid - howMuchPersonBShouldPay;
		if(refundForPersonBNeeded<0)
			tmpToReturn = 0.0;

		tmpToReturn = howMuchPersonBPaid - pd.getHowMuchPersonShouldPay();
		if(tmpToReturn<0)
			tmpToReturn = 0;
		return tmpToReturn;
	}

	private static boolean personBWantsLessThanIHaveToReturn(
			PersonData pd, double howMuchPersonBPaid, double howMuchPersonBShouldPay, double tmpToReturn, double howMuchRefundPersonBNeeds) {
		return howMuchRefundPersonBNeeds < pd.getHowMuchPersonShouldPay() - pd.getHowMuchIPaid();
	}

	private static double splitMyReturnAmount(PersonData pd) {
		double tmpToReturn;
		tmpToReturn = pd.getToReturn() - pd.getAlreadyReturned();
		return tmpToReturn;
	}
	
	private static boolean personBNeedsMoreThanIShouldGive(double tmpToReturn, double alreadyReturned, double toReturn) {
		return alreadyReturned + tmpToReturn > toReturn;
	}
	
	public static List<AtomPayment> getDefaultAtomPaymentsList(double payment) {
		return getDefaultAtomPaymentsList("", payment);
	}

	public static List<AtomPayment> getDefaultAtomPaymentsList(String name, double payment) {
		List<AtomPayment> atomPayments = new ArrayList<AtomPayment>();
		atomPayments.add(new AtomPayment(name, payment));
		return atomPayments;
	}
}
