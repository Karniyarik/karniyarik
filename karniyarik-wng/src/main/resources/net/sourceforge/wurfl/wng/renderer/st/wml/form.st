<p>
  $children$
  <anchor>
    <go method="$component.method$" href="$component.action$">
    	$hiddenfields:{<postfield name="$it.name$" value="$it.value$"/>}; separator="\n"$
    	$postfields:{<postfield name="$it.name$" value="\$($it.name$)"/>}; separator="\n"$
    </go>
    $component.submit.value$    
  </anchor>
</p>
