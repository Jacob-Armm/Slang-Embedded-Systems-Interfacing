# Introduction

**SESI** (Slang Embedded Systems Interacing) is a tool that provides the ability for rapid prototyping on physical systems for the Sireum Program Transformation, Analysis, and Verification Framework. Rapid prototyping is accomplished by using platforms that can translate commands from an abstract represtentation of a system into an acceptable format for different systems such as Firmata or GUI simulations.

This tool works in tadnem with HAMR framework which is a code generation and system build framework for embedded systems whose architecture is specified using the Architecture Analysis and Design Language.

# Abstractions

SESI consists of five levels of abstraction which are the HAMR Generated Code, Device Bridge, Abstract Device Layer, Abstract-Physical Association, and Physical Device Layer. The first layer is neccesary for interaction between HAMR and SESI while the last four layers are built into or required by SESI.

## HAMR Generated Code

This layer is where the behavior of the system is defined. This layer makes calls to the device bridge to send commands and request information from the physical system. 

## Device Bridge

This layer is used to define and initialize the abstract structre of the system using SESI. This layer can also provides objects that define how HAMR generated components will interacts with the abstract architecture by defining what abstract device symbloizes the component and tranlating data into acceptable formats when data is passed between the components and the abstarct device.

## Abract Device Layer

This layer is used to translate commands from the abract devices into pin commands that can be used in the Abstract-Physical Assocition layer. While this layer is arbitrary since the pin commands can be called directly from the device bridge layer, this layer is still used to encapsulate the device behavior and provide a sense of parity.

## Abstract-Physical Association

This layer uses the chosen platform and pin commands provided by the abstract device layer to translate the pin commands into valid commands that can be handled by the architecture the platform is defined for. This layer is used to establish parity between the abstract and physical device layers.

## Physical Device Layer

This layer represents the physical implementation of the system such as a embedded board or GUI. This layer takes in commands from the Abstract-Physical Association and responds the commands by carrying out some behavior such as sending and recieiving data to and from the abract representation.

# How to use SESI

In this section we will discuss how to use SESI both with and without HAMR

## SESI Without HAMR

To desicribe how to use SESI without HAMR, we will look at a basic stepper motor program.

### Defining Pins

First, you define all the abract pins that will be used by the abract devices.

```
    val SMn1: Pin = Pin("n1", PinMode.OUTPUT)
    val SMn2: Pin = Pin("n2", PinMode.OUTPUT)
    val SMn3: Pin = Pin("n3", PinMode.OUTPUT)
    val SMn4: Pin = Pin("n4", PinMode.OUTPUT)
```

In this case we defined 4 pins that are set to digital output.

### Defining a PinMap and Device Set

We will now define a pinMap to define an numerical id for abstract pin that can represent things such as a specific pin on an embedded board.

```
    val pmap1: Map[String, Z] = Map.empty[String, Z] ++ ISZ(
      "n1" ~> 11,
      "n2" ~> 10,
      "n3" ~> 9,
      "n4" ~> 8
    )

    val dset: DeviceSet = DeviceSet("dset1", implGetter.getImpl(pmap1), None())
```

In this case the pinMap will match these pins to one deviceSet which is used to tie pins to a specific instance of a platform. In essence this device set matches these four pins to one firmata instance.

**Note: impGetter.getImpl(pmap1) is used to retireve platforms that are not written in slang so that they can be used in a slang context**

### Initializing the Abract-Physical Association

To initialize the abstract to physical communication layer you call LPConn.init with the list of all device sets and pins. This method initializes all platforms and creates a universal pin map which acts similarly to the normal pinMap. The difference is that
the universal pinMap, maps pin alias to both platform and id number instead of just a pin number. This is done to direct the flow 
of pin commands to their respective platform when calling read and write.

```
   LPConn.init(ISZ(dset), ISZ(SMn1, SMn2, SMn3, SMn4))
```

### Initializing Devices

Now that the architecture and all platforms have been instantiated, we can now define the abstract devices which act as proxies for the real devices by defining how certain actions will be translated to pin commands that will be handled by the underlying abract pins.

```
    val SM: StepperMotor = StepperMotor(60, 4, SMn1, SMn2, SMn3, SMn4)
```

### Behavior

Now that the entire architecture has been defined we can write the behavior for the system.

```
    if(LPConn.ready) { //we need to ensure that all platforms are read before trying to make calls to the proxies
      while(T) {
        SM.step(2) //step make the stepper motor take n number of steps
        SM.setRPM(3) //setRPM changes the RPM of the stepper motor
        SM.step(-2) //when steps is called with a negative number, the stepper motor rotates in the opisite direction
        SM.setRPM(SM.defaultRPM)
      }
    }
```

## SESI With HAMR



# How to Extend SESI



## How to Add New Devices



## How to Add New Platforms



