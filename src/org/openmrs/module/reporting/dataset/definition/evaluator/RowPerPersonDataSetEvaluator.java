/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.reporting.dataset.definition.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.definition.RowPerObjectDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.RowPerPersonDataSetDefinition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.idset.EvaluatedIdSet;
import org.openmrs.module.reporting.idset.IdSet;
import org.openmrs.module.reporting.idset.service.IdSetDefinitionService;

/**
 * The logic that evaluates a {@link RowPerObjectDataSetDefinition} and produces an {@link DataSet}
 */
public abstract class RowPerPersonDataSetEvaluator implements DataSetEvaluator {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * Public constructor
	 */
	public RowPerPersonDataSetEvaluator() { }
	
	/**
	 * Implementations of this method should evaluate the appropriate id filters in the DataSetDefinition and
	 * populate these IdSets within the Context
	 */
	public void populateFilterIdSets(RowPerObjectDataSetDefinition<?> dsd, EvaluationContext context) throws EvaluationException {
		RowPerPersonDataSetDefinition rpp = (RowPerPersonDataSetDefinition) dsd;
		EvaluatedIdSet personIdSet = Context.getService(IdSetDefinitionService.class).evaluate(rpp.getPersonFilter(), context);
		context.getBaseIdSets().put(Person.class, personIdSet.getIdSet());
	}
	
	/**
	 * Implementations of this method should return the base IdSet that is appropriate for the passed DataSetDefinition
	 */
	public IdSet getBaseIdSet(RowPerObjectDataSetDefinition<?> dsd, EvaluationContext context) {
		return context.getBaseIdSets().get(Person.class);
	}
}
