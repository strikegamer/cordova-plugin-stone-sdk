#import <Cordova/CDV.h>
#import "StoneSDK/StoneSDK.h"

@interface StoneSDK : CDVPlugin

- (void) validation:(CDVInvokedUrlCommand*)command;
- (void) device:(CDVInvokedUrlCommand*)command;
- (void) transaction:(CDVInvokedUrlCommand*)command;
- (void) transactionList:(CDVInvokedUrlCommand*)command;

@property (strong, nonatomic) UIView *overlayView;
@property (strong, nonatomic) UIActivityIndicatorView *activityIndicator;

@end
