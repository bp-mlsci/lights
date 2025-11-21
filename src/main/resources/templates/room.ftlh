<@m.page title="${room.label} Light Control">

<@m.colorControl href="/roomall/${room.name()}">

<@m.link label="All Lights In ${room.label} Off" href="/roomoff/${room.name()}"/>

<@m.link label="All Lights In ${room.label} On Automatic Schedule" href="/roomresume/${room.name()}"/>

</@m.colorControl>

</@m.page>