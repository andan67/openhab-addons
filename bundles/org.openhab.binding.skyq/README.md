# SkyQ Binding

This binding provides control functions for SkyQ receivers.

## Supported Things

SkyQ receiver.

## Discovery

Currently no discovery is implemented

## Thing Configuration

Use the UI to add the receiver as a thing and provide the following configuration options

| Parameter         | Required | Default            | Description                                                                                                                                          |
|-------------------|----------|--------------------|-------------------------------------------------------|
| `Hostname`        | yes      | N/A                | The IP address of the SkyQ receiver to control        |
| `MAC Address`     | no       | "" (empty string) | The MAC address of the SkyQ receiver to control        |
| `Enable Configurable Preset` | no       | true     | Enables the configuration of the channel preset (see below)     |
| `Refresh Interval`        | yes      | 30 | The interval for refreshing the item states in seconds       |
| `Retry Polling`    | yes       | 60  | The interval for retrying the connection to the recevier after it went offline |
| `Status Check Interval`    | yes       | 60 | The interval for checking if the receiver is reachable (online)  |

## Channels

| Channel  | Type   | Description                                                                                                                                                        |
|----------|--------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| control#controlCommand  | String | Sends remote control commands to the device (see below)                                                                                                            |
| control#channelPreset  | String | Holds list of all channels from receiver and allows switch to a selected channel. The list and order of the channels can be configured through a file (see below). 
| control#channelFavorites  | String | Holds list of favorite channels from receiver and allows switch to a selected channel.                                                                             
| control#power | Switch | Switches the receiver ON or OFF. Your receiver has in network standby for this to work.                                                                            |
| statusChannels#currentChannelTitle | String | Shows current live TV channel title if available                                                                                                                   |
| statusChannels#powerStatus | String | Shows power state ON, OFF, or STANDBY                                                                                                                              |
| statusChannels#currentTransportState | String | State of current transport stream (PLAYING etc)                                                                                                                    |

## Control commands

The `control#controlCommand` channel supports the following commands that are defined as state options:

| Value       | Label   | Description                  |
|-------------|--------|------------------------------|
| power       | Power ||
| select      | Select ||
| backup      | Back/Up ||
| dismiss     | Dismiss ||
| channelup   | Channel up ||
| channeldown |Channel down ||
| interactive | Interactive ||
| sidebar     | Sidebar ||
| help        | Help ||
| services    | Services ||
| search      | Search ||
| tvguide     | TV guide ||
| home        | Home ||
| i           | i ||
| text        |Text ||
| up          | Up ||
| down        | Down ||
| left        | Left ||
| right       | Right ||
| red         | Red ||
| green       | Green ||
| yellow      | Yellow ||
| blue        | Blue ||
| 0           | 0 ||
| 1           | 1 ||
| 2           | 2 ||
| 3           | 3 ||
| 4           | 4 ||
| 5           | 5 ||
| 6           | 6 ||
| 7           | 7 ||
| 8           | 8 ||
| 9           | 9 ||
| play        | Play ||
| pause       | Pause ||
| stop        | Stop ||
| record      | Record ||
| fastforward | Fast forward  ||
| rewind      | Rewind ||
| boxoffice   | Box office ||
| sky         | Sky ||

### Defining macros
The `controlCommand` channel supports sending multiple commands to the device. This allows the definition of macros by the following procedure:

1. Define a `String` typed item linked to the `controlCommand` channel
2. Add `State Description` metadata to the item
3. A macro is defined by each option (line) within the `Options` section. The option value represents the 
semicolon separated sequence of commands of the macro, and the label the macro name
4. A special command `sleep` is used to delay the sending of the commands by a fixed but short period. This might be required for certain sequences of commands 
because sometime some commands are swallowed by the box.

#### Example

State options that define macros for opening the prime video app of for showing the top series page 
```
interactive;down;down;down;select=prime video
home;sleep;down;down;down;sleep;right;right=Top Series
```
Note the `sleep` command here which is required to get reproducable results.

## Configurable channel presets

To ease the selection of a large number of TV preset channels, the channels for selection can be filtered and sorted by use of a configuration file.
This feature can be enabled by setting the thing configuration `Enable Configurable Presets` and saving the new configuration.

If this feature is enabled, the complete channel list as requested from the receiver on each initialization will be stored
as a csv formatted file `/userdata/config/skyq/channel_presets.csv`.

To filter and sort the channels list, edit the file and set the value of the last column named `Rank` as follows: 


| Rank value   | Effect   |
|---------------|----------|
| `< 0`| channel is excluded |
|  `0` | channel is added to end of preset list |
| `> 0` | channel is added in the order of the rank value |

Channels with same rank value will be ordered by the display number.

A refresh of the preset channels after a change of the csv file can be triggered by issuing the command `--REFRESH--` which is added as first 
pseudo channel to the list.

## Full Example

skyq.things:

```
Thing skyq:skyqreceiver:skyq1 [ hostname="192.168.178.2", configurrblePresets=true, retryInterval=60, checkStatusInterval=60, refreshInterval=30 ]
```

skyq.items:

```
String sky_command "[%s]" {channel="skyq:skyqreceiver:skyq1:control#controlCommand"}
```

## Main UI Examples

### Control command cell

When clicked, a control command is send to the receiver on selection from a drop down list

#### YAML definition of cell

```
component: oh-label-cell
config:
  action: options
  actionItem: SkyQReceiver_ControlReceiverCommand
  expandable: false
  item: SkyQReceiver_ControlReceiverCommand
  stateAsHeader: true
  title: Control Receiver Comman
```

### Preset cell

When clicked, a custom widget is opened with a list of the possibly filtered and ordered list of channels.
This list can be further filtered. On selection of a channel item, the receiver will switch to this channel. 

#### YAML definition of cell

```
component: oh-label-cell
config:
  action: popup
  actionModal: widget:widget_tv_preset
  actionModalConfig:
    item: SkyQReceiver_ChannelPreset
    prop1: Select channel
  expandable: false
  item: SkyQReceiver_ChannelPreset
  title: TV Channel
```

#### YAML definition of custom widget

```
uid: widget_tv_preset
tags: []
props:
  parameters:
    - description: A text prop
      label: Prop 1
      name: prop1
      required: false
      type: TEXT
    - context: item
      description: An item to control
      label: Item
      name: item
      required: false
      type: TEXT
  parameterGroups: []
timestamp: Nov 19, 2021, 5:34:29 PM
component: f7-card
config:
  title: =props.prop1
slots:
  default:
    - component: f7-list
      config:
        virtualList: true
      slots:
        before-list:
          - component: oh-input-item
            config:
              icon: f7:search
              iconColor: gray
              outline: true
              placeholder: Suche
              type: text
              variable: channelFilter
        default:
          - component: oh-repeater
            config:
              filter: loop.option.label.toLowerCase().includes(vars.channelFilter.toLowerCase()) == true
              for: option
              fragment: true
              itemOptions: =props.item
              sourceType: itemStateOptions
            slots:
              default:
                - component: oh-list-item
                  config:
                    action: command
                    actionCommand: =loop.option.value
                    actionItem: =props.item
                    popupClose: true
                    title: =loop.option.label

```


