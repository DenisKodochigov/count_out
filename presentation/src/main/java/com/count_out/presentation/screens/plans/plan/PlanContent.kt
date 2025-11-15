package com.count_out.presentation.screens.plans.plan

//@Composable fun PlanContent(dataState: PlansState, plan: Plan) {
//    CarcassPlanScreen(
//        namePlanField = { NamePlanField(dataState, plan) },
//        partList = { ListPlan(dataState, plan) },
//        iconsActionPlan = { IconsActionPlan(dataState, plan) }
//    )
//}
//@Composable fun IconsActionPlan(dataState: PlansState, plan: Plan){
//    IconsGroup(
//        onSpeech = { showSpeech(dataState, plan) },
//        onDelete = { dataState.event(PlansEvent.DelPlan(plan)) }
//    )
//}
//@Composable fun NamePlanField(dataState: PlansState, plan: Plan){
//    val enteredName: MutableState<String> = remember { mutableStateOf(plan.name) }
//    if (plan.idPlan == 0L) return
//    TextFieldApp(
//        modifier = Modifier.padding(start = 14.dp),
//        edit = true,
//        typeKeyboard = TypeKeyboard.TEXT,
//        contentAlignment = Alignment.CenterStart,
//        textStyle = MaterialTheme.typography.headlineMedium.copy(textAlign = TextAlign.Start),
//        colorLine = MaterialTheme.colorScheme.outline,
//        placeholder = enteredName.value,
//        onChangeFocus = {
//            enteredName.value = it
//            dataState.event(PlansEvent.UpdatePlanName(NameId(enteredName.value,plan.idPlan)))
//
//        }
//    )
//}
//@Composable fun ListPlan(dataState: PlansState, plan: Plan){
//    plan.parts.forEach { part ->
//        Spacer(modifier = Modifier.height(8.dp))
//        PartContent(dataState = dataState, part = part)
//    }
//}
