$if(component.src)$
<script type="$component.type$" src="$component.src$" />
$else$
<script type="$component.type$">
$component.content$
</script>

$endif$