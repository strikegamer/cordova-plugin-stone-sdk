#import "StoneSDK.h"

@implementation StoneSDK

- (void)validation:(CDVInvokedUrlCommand*)command {

    [self.overlayView addSubview:self.activityIndicator];
    [self.activityIndicator startAnimating];
//    [self.navigationController.view addSubview:self.overlayView];

    // Recebe o Stone Code
    NSString* stoneCodeList = [[command arguments] objectAtIndex:0];
    NSLog(@"Stone Code, %@", stoneCodeList);

    // Ativando o Stone Code;
    [STNStoneCodeActivationProvider activateStoneCode:stoneCodeList withBlock:^(BOOL succeeded, NSError *error) {
        [self.overlayView removeFromSuperview];
        if (succeeded) {
            NSLog(@"Stone Code ativado com sucesso.");
            UIAlertView *success = [[UIAlertView alloc]
                                  initWithTitle:@"Ativado!"
                                  message:@"Stone Code ativado com sucesso.!"
                                  delegate:self
                                  cancelButtonTitle:@"Ok"
                                  otherButtonTitles:nil];
            [success show];
        } else {
            NSLog(@"%@", error.description);
            NSString* msg = error.description;
            UIAlertView *fail = [[UIAlertView alloc]
                                  initWithTitle:@"Oops!"
                                  message:msg
                                  delegate:self
                                  cancelButtonTitle:@"Ok"
                                  otherButtonTitles:nil];
            [fail show];
        }
    }];
}

- (void)device:(CDVInvokedUrlCommand*)command {

    [self.overlayView addSubview:self.activityIndicator];
    [self.activityIndicator startAnimating];
//    [self.navigationController.view addSubview:self.overlayView];

    NSLog(@"Efetuando conexão com pinpad.");

    // Efetua a conexão com o pinpad
    [STNPinPadConnectionProvider connectToPinpad:^(BOOL succeeded, NSError *error) {

        [self.overlayView removeFromSuperview];

        if (succeeded) {
            NSLog(@"Conectado com sucesso!");
            UIAlertView *success = [[UIAlertView alloc]
                                    initWithTitle:nil
                                    message:@"Pinpad conectado!"
                                    delegate:self
                                    cancelButtonTitle:@"Ok"
                                    otherButtonTitles:nil];
            [success show];
        } else {
            NSLog(@"%@", error.description);
            NSString* msg = error.description;
            UIAlertView *fail = [[UIAlertView alloc]
                                 initWithTitle:@"Oops!"
                                 message:msg
                                 delegate:self
                                 cancelButtonTitle:@"Ok"
                                 otherButtonTitles:nil];
            [fail show];
        }
    }];
}

- (void)transaction:(CDVInvokedUrlCommand*)command {
    [self.overlayView addSubview:self.activityIndicator];
    [self.activityIndicator startAnimating];
//    [self.navigationController.view addSubview:self.overlayView];

    // Recebe o valor do Plugin
    NSString* amount = [[command arguments] objectAtIndex:0];
    NSLog(@"Amount, %@", amount);

    /*
     O valor da transação deve ser sempre por CENTAVOS e para isso
     devemos utilizar com um int no objeto da transação;
     */
    float realValue = [self convertToFloat:amount] / 100;
    NSLog(@"realValue, %f", realValue);
    float centsFromFloatValue = 100 * realValue;
    NSLog(@"centsFromFloatValue, %f", centsFromFloatValue);
    int justCents = (int) centsFromFloatValue;
    NSLog(@"justCents, %i", justCents);

    // Iniciando o modelo transaction para efetivar a transacao;
    STNTransactionModel *transaction = [[STNTransactionModel alloc] init];

    // Propriedade Obrigatória, deve conter o valor da transação em centavos. (EX. R$ 56,45 = 5645);
    transaction.amount = [NSNumber numberWithInteger:justCents];

    // Recebe o método de pagamento do Plugin
    NSString* method = [[command arguments] objectAtIndex:1];
    NSLog(@"Method, %@", method);

    // Recebe o numero de parcelas do Plugin
    NSString* instalments = [[command arguments] objectAtIndex:2];
    NSLog(@"Instalments, %@", instalments);

    // Verifica se é DÉBITO ou CRÉDITO.
    if ([method  isEqual: @"DEBIT"]) { // é Débito

        NSLog(@"Entrou no débito");

        instalments = @"1";
        int intInstalments = [instalments integerValue];

        // Propriedade Obrigatória, define o número de parcelas da transação;
        transaction.instalmentAmount = intInstalments;

        // Propriedade Obrigatória, define o tipo de transação, se é débito ou crédito;
        transaction.type = STNTransactionTypeSimplifiedDebit;

    } else if ([method  isEqual: @"CREDIT"]) { // é Crédito
        NSLog(@"Entrou no crédito");
        // Propriedade Obrigatória, define o tipo de transação, se é débito ou crédito;
        transaction.type = STNTransactionTypeSimplifiedCredit;

        if ([instalments isEqual: @"ONE_INSTALMENT"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountOne;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"ONE_INSTALMENT");
        } else if ([instalments isEqual: @"TWO_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountTwo;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"TWO_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"THREE_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountThree;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"THREE_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"FOUR_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountFour;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"FOUR_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"FIVE_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountFive;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"FIVE_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"SIX_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountSix;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"SIX_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"SEVEN_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountSeven;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"SEVEN_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"EIGHT_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountEight;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"EIGHT_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"NINE_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountNine;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"NINE_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"TEN_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountTen;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"TEN_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"ELEVEN_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountEleven;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"ELEVEN_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"TWELVE_INSTALMENT_NO_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountTwelve;
            transaction.instalmentType = STNInstalmentTypeNone;
            NSLog(@"TWELVE_INSTALMENT_NO_INTEREST");
        } else if ([instalments isEqual: @"TWO_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountTwo;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"TWO_INSTALMENT_WITH_INTEREST");
        } else if ([instalments isEqual: @"THREE_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountThree;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"THREE_INSTALMENT_WITH_INTEREST");
        } else if ([instalments isEqual: @"FOUR_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountFour;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"FOUR_INSTALMENT_WITH_INTEREST");
        } else if ([instalments isEqual: @"FIVE_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountFive;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"FIVE_INSTALMENT_WITH_INTEREST");
        } else if ([instalments isEqual: @"SIX_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountSix;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"SIX_INSTALMENT_WITH_INTEREST");
        } else if ([instalments isEqual: @"SEVEN_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountSeven;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"SEVEN_INSTALMENT_WITH_INTEREST");
        } else if ([instalments isEqual: @"EIGHT_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountEight;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"EIGHT_INSTALMENT_WITH_INTEREST");
        } else if ([instalments isEqual: @"NINE_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountNine;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"NINE_INSTALMENT_WITH_INTEREST");
        } else if ([instalments isEqual: @"TEN_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountTen;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"TEN_INSTALMENT_WITH_INTEREST");
        } else if ([instalments isEqual: @"ELEVEN_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountEleven;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"ELEVEN_INSTALMENT_WITH_INTEREST");
        } else if ([instalments isEqual: @"TWELVE_INSTALMENT_WITH_INTEREST"]) {
            transaction.instalmentAmount = STNTransactionInstalmentAmountTwelve;
            transaction.instalmentType = STNInstalmentTypeMerchant;
            NSLog(@"TWELVE_INSTALMENT_WITH_INTEREST");
        } else {
            NSLog(@"Escolha uma opção válida");
        }
    } else {
        NSLog(@"Opção Inválida");
    }

    // Vamos efetivar a transacao;
    [STNTransactionProvider sendTransaction:transaction withBlock:^(BOOL succeeded, NSError *error) {
        [self.overlayView removeFromSuperview];
        if (succeeded) {
            NSLog(@"Transacao realizada com sucesso.");
            UIAlertView *success = [[UIAlertView alloc]
                                    initWithTitle:nil
                                    message:@"Transacao realizada com sucesso!"
                                    delegate:self
                                    cancelButtonTitle:@"Ok"
                                    otherButtonTitles:nil];
            [success show];
        } else {
            NSLog(@"Ocorreu uma falha na transacao. [%@]", error);
            NSString* msg = error.description;
            UIAlertView *fail = [[UIAlertView alloc]
                                 initWithTitle:@"Oops!"
                                 message:msg
                                 delegate:self
                                 cancelButtonTitle:@"Ok"
                                 otherButtonTitles:nil];
            [fail show];
        }
    }];

}

- (float)convertToFloat:(NSString*)fromFormatedString {
    NSLog(@"String formatada");
    NSMutableString *textFieldStrValue = [NSMutableString stringWithString:fromFormatedString];

    NSNumberFormatter *numberFormatter = [[NSNumberFormatter alloc] init];
    [numberFormatter setNumberStyle:NSNumberFormatterDecimalStyle];
    [numberFormatter setMaximumFractionDigits:2];
    [numberFormatter setMinimumFractionDigits:2];

    [textFieldStrValue replaceOccurrencesOfString:numberFormatter.groupingSeparator
                                       withString:@""
                                          options:NSLiteralSearch
                                            range:NSMakeRange(0, [textFieldStrValue length])];

    // Muda o separador decimal para ponto caso esteja numa localidade que use vírgula.
    [textFieldStrValue replaceOccurrencesOfString:numberFormatter.decimalSeparator
                                       withString:@"."
                                          options:NSLiteralSearch
                                            range:NSMakeRange(0, [textFieldStrValue length])];

    float textFieldNum = [[NSDecimalNumber decimalNumberWithString:textFieldStrValue] floatValue];

    return textFieldNum;
}

- (void)transactionList:(CDVInvokedUrlCommand*)command {

    NSString* msg = [NSString stringWithFormat: @"Hello"];

    CDVPluginResult* result = [CDVPluginResult
                               resultWithStatus:CDVCommandStatus_OK
                               messageAsString:msg];

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end
